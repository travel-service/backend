package com.trablock.web.controller;

import com.trablock.web.dto.location.*;
import com.trablock.web.repository.location.LocationRepository;
import com.trablock.web.service.location.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static com.trablock.web.domain.LocationType.*;


@RestController
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;
    private final LocationRepository locationRepository; // health-check용. 지울 것.

    /**
     * 로케이션의 details를 반환
     * -> 지도의 markup이나 block 을 클릭할 때 response
     *
     * @param locationId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/locations/{locationId}", method = RequestMethod.GET)
    public ResponseEntity<Object> viewLocationDetails(@PathVariable("locationId") Long locationId, @RequestBody LocationTypeDto locationTypeDto) {
        System.out.println("type = " + locationTypeDto);
        return ResponseEntity.ok(locationService.getLocationDetails(locationId, locationTypeDto.getType()));
    }

    /**
     * 지도에 표시
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/locations/mark", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> viewMarkLocationsOnMap() {
        return ResponseEntity.ok().body(locationService.getMarkLocationList());
    }


    /**
     * 블록 형태로 표시
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/locations/block", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> viewBlockLocationList() {
        return ResponseEntity.ok().body(locationService.getBlockLocationList());
    }

    /**
     * 멤버 로케이션 추가
     * 멤버 로케이션 생성 시, Location이 먼저 생성되고
     * 그 이후 TypeLocation, Information, MemberLocation이 순차적으로 생성되어야 한다.
     */
    @RequestMapping(value = " /members/location", method = RequestMethod.POST)
    public ResponseEntity<HashMap<String, Object>> memberLocationAdd(@RequestBody LocationSaveWrapperDto locationSaveWrapperDto) {

        locationService.createLocationByMember(locationSaveWrapperDto);

//        return ResponseEntity.ok().body(); // 반환값을 MarkLoc, BlockLoc, memberLoc 셋 다 주자
        return null;
    }


    @ResponseBody
    @RequestMapping(value = "/locations/health-check", method = RequestMethod.GET)
    public Object health() {
        return locationRepository.findAllByTypeAndIsMemberFalse(ATTRACTION, MarkLocationView.class);
    }

    @ResponseBody
    @RequestMapping(value = "/locations/health-check2", method = RequestMethod.GET)
    public Object health2() {
        return locationRepository.findById(1L);
    }


    @ResponseBody
    @RequestMapping(value = "/locations/repo-test", method = RequestMethod.GET)
    public Object repo_test() {
        return locationRepository.findAttractionByLocationId(1L);
    }


    /**
     * MemberLocation과 관련된 문제?
     * =============================
     * 남이 공개한거를 가져올수있다 문제x
     * 남이 공개 안한거를 가져올수있다 문제o
     * 내가 공개한거를 내가 가져온다 문제x
     * 내가 공개한거를 내가 못가져온다 문제o
     */
}

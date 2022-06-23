package com.trablock.web.controller;

import com.trablock.web.domain.LocationType;
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
    public ResponseEntity<Object> viewLocationDetails(@PathVariable("locationId") Long locationId, @RequestBody LocationType locationType) {

        return ResponseEntity.ok(locationService.getLocationDetails(locationId, locationType));
    }

    /**
     * 지도에 표시하기 위한 Location의 DTO.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/locations/mark", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> viewMarkLocationsOnMap() {
        return ResponseEntity.ok().body(locationService.getMarkLocationList());
    }


    /**
     * 블럭형태로 표시하기 위한 Location의 DTO
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
     *
     * @param formData
     * @return
     */
    @RequestMapping(value = " /members/location", method = RequestMethod.POST)
    public ResponseEntity<HashMap<String, Object>> memberLocationAdd(@RequestBody HashMap<String, Object> formData) {
        LocationDto locationDto = locationService.createLocation((LocationSaveRequestDto) formData.get("location"));
        LocationType type = locationDto.getType();
        //Object typeLocationDto = typeLocationService.createTypeLocation((TypeLocationSaveRequestDto) formData.get("typeLocation"), locationDto.getId(), type);
        MemberLocationDto memberLocationDto = locationService.createMemberLocation((MemberLocationSaveRequestDto) formData.get("memberLocation"));

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("location", locationDto);
        //map.put(String.valueOf(type), typeLocationDto);
        map.put("member-location", memberLocationDto);

        // 타입 로케이션도 만들어야 함
        return ResponseEntity.ok().body(map); // 반환값을 MarkLoc, BlockLoc, memberLoc 셋 다 주자
    }


    @ResponseBody
    @RequestMapping(value = "/locations/health-check", method = RequestMethod.GET)
    public Object health() {
        return locationRepository.findAllByTypeAndIsMemberFalse(ATTRACTION, MarkLocationDto.class);
    }

    @ResponseBody
    @RequestMapping(value = "/locations/health-check2", method = RequestMethod.GET)
    public Object health2() {
        return locationRepository.findById(1L);
    }


    @ResponseBody
    @RequestMapping(value = "/locations/repo-test", method = RequestMethod.GET)
    public Object repotest() {
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

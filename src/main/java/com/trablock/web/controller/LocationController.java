package com.trablock.web.controller;

import com.trablock.web.domain.LocationType;
import com.trablock.web.dto.location.*;
import com.trablock.web.service.location.LocationService;
import com.trablock.web.service.location.LocationServiceImpl;
import com.trablock.web.service.location.TypeLocationService;
import com.trablock.web.service.location.TypeLocationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import static com.trablock.web.domain.LocationType.*;


@RestController
public class LocationController {

    private final LocationService locationService;
    private final TypeLocationService typeLocationService;

    /**
     * 생성자 DI
     *
     * @param locationService
     */
    public LocationController(LocationServiceImpl locationService, TypeLocationServiceImpl typeLocationService) {
        this.locationService = locationService;
        this.typeLocationService = typeLocationService;
    }

    @ResponseBody
    @RequestMapping(value = "/locations/{locationId}", method = RequestMethod.GET)
    public HashMap<String, Object> viewLocationDetails(@PathVariable("locationId") Long locationId) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        LocationDto locationDto = locationService.getLocationDetails(locationId);
        LocationType type = locationDto.getType();
        Object details = typeLocationService.getLocationDetails(locationId, type);
        map.put("location", locationDto);
        map.put(String.valueOf(type), details);

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/locations", method = RequestMethod.GET)
    public HashMap<String, Object> viewAll() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("Lodge", locationService.getMarkLocationsWithType(LODGE));
        map.put("Restaurant", locationService.getMarkLocationsWithType(RESTAURANT));
        map.put("Attraction", locationService.getMarkLocationsWithType(ATTRACTION));
        map.put("Culture", locationService.getMarkLocationsWithType(CULTURE));
        map.put("Festival", locationService.getMarkLocationsWithType(FESTIVAL));
        map.put("Leports", locationService.getMarkLocationsWithType(LEPORTS));
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/simples", method = RequestMethod.GET)
    public List<MarkLocationDto> viewSimple() {
        return locationService.getMarkLocationDtos();
    }

    /**
     * 요구사항 맞추려면 dto 덩어리는 List가 아니라 HashSet으로 반환하는게 좋을 것 같다
     * 캐스팅을 이용하자..
     * https://stackoverflow.com/questions/1429860/easiest-way-to-convert-a-list-to-a-set-in-java
     */

    @RequestMapping(value = " /members/location", method = RequestMethod.POST)
    public ResponseEntity<HashMap<String, Object>> memberLocationAdd(@RequestBody HashMap<String, Object> formData) {
        LocationDto locationDto = locationService.createLocation((LocationSaveRequestDto) formData.get("location"));
        LocationType type = locationDto.getType();
        Object typeLocationDto = typeLocationService.createTypeLocation((TypeLocationSaveRequestDto) formData.get("typeLocation"), locationDto.getId(), type);
        MemberLocationDto memberLocationDto = locationService.createMemberLocation((MemberLocationSaveRequestDto) formData.get("memberLocation"));


        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("location", locationDto);
        map.put(String.valueOf(type), typeLocationDto);
        map.put("member-location", memberLocationDto);

        // 타입 로케이션도 만들어야 함
        return ResponseEntity.ok().body(map); // 반환값을 MarkLoc, BlockLoc, memberLoc 셋 다 주자
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

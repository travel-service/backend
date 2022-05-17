package com.trablock.web.controller;

import com.trablock.web.domain.LocationType;
import com.trablock.web.dto.location.LocationDto;
import com.trablock.web.dto.location.SimpleLocationDto;
import com.trablock.web.entity.location.Location;
import com.trablock.web.repository.location.LocationRepository;
import com.trablock.web.service.location.LocationService;
import com.trablock.web.service.location.LocationServiceImpl;
import com.trablock.web.service.location.TypeLocationService;
import com.trablock.web.service.location.TypeLocationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.trablock.web.domain.LocationType.*;


@RestController
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

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
        map.put("Lodge", locationService.viewSimple(LODGE));
        map.put("Restaurant", locationService.viewSimple(RESTAURANT));
        map.put("Attraction", locationService.viewSimple(ATTRACTION));
        map.put("Culture", locationService.viewSimple(CULTURE));
        map.put("Festival", locationService.viewSimple(FESTIVAL));
        map.put("Leports", locationService.viewSimple(LEPORTS));
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/simples", method = RequestMethod.GET)
    public List<SimpleLocationDto> viewSimple() {
        return locationService.getSimpleLocations();
    }

    @ResponseBody
    @RequestMapping(value = "/view-test", method = RequestMethod.GET)
    public Location viewTest() {
        Location location = locationRepository.findByName("섭지코지").get();
        return location;
    }


    /** 요구사항 맞추려면 dto 덩어리는 List가 아니라 HashSet으로 반환하는게 좋을 것 같다
     *     캐스팅을 이용하자..
     *     https://stackoverflow.com/questions/1429860/easiest-way-to-convert-a-list-to-a-set-in-java
     */
}

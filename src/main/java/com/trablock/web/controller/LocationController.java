package com.trablock.web.controller;

import com.trablock.web.service.location.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static com.trablock.web.entity.location.LocationType.*;

@RequiredArgsConstructor
@RestController
public class LocationController {

    private final LocationService locationService;

    @ResponseBody
    @RequestMapping(value = "api/locations", method = RequestMethod.GET)
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

    /** 요구사항 맞추려면 dto 덩어리는 List가 아니라 HashSet으로 반환하는게 좋을 것 같다
     *     캐스팅을 이용하자..
     *     https://stackoverflow.com/questions/1429860/easiest-way-to-convert-a-list-to-a-set-in-java
     */
}

package com.trablock.web.controller;

import com.trablock.web.service.location.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LocationController {

    private final LocationService locationService;

    //@GetMapping("")
    //public
}

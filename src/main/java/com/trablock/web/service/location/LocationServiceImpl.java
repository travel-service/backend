package com.trablock.web.service.location;

import com.trablock.web.repository.location.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationServiceImpl {

    private final LocationRepository locationRepository;


}

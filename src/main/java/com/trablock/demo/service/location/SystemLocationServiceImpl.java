package com.trablock.demo.service.location;

import com.trablock.demo.domain.location.Location;
import com.trablock.demo.domain.location.SystemLocation;
import com.trablock.demo.repository.location.SystemLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SystemLocationServiceImpl implements LocationService {

    private final SystemLocationRepository locationRepository;

    public List<SystemLocation> findAll() {

        return locationRepository.findAll();
    }

//    public SystemLocation findOne(Long locationId) {
//
//        return locationRepository.findOne(locationId);
//    }


//    public List<SystemLocation> findByAreaCode(int areaCode) {
//        return locationRepository.findByAreaCode(areaCode);
//    }

}

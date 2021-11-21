package com.trablock.demo.service.location;

import com.trablock.demo.domain.location.Location;
import com.trablock.demo.domain.location.MemberLocation;
import com.trablock.demo.repository.location.LocationRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberLocationServiceImpl {

    private final LocationRepositoryImpl locationRepository;

    /**
     * 사용자 로케이션 등록
     * @param location
     * @return
     */
    @Transactional
    public Long enroll(MemberLocation location) {

        locationRepository.save(location);
        return location.getId();
    }

    public List<Location> findLocations() {
        return locationRepository.findAll();
    }

    public Location findOne(Long locationId) {
        return locationRepository.findOne(locationId);
    }


}

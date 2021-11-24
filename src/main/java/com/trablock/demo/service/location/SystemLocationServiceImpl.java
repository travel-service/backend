package com.trablock.demo.service.location;

import com.trablock.demo.domain.location.Location;
import com.trablock.demo.repository.location.LocationRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SystemLocationServiceImpl implements LocationService {

    private final LocationRepositoryImpl locationRepository;

    @Override
    public List<Location> findLocations() {
        return null;
    }

    @Override
    public Location findOne(Long locationId) {
        return null;
    }
}

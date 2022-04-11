package com.trablock.web.service.location;

import com.trablock.web.dto.LocationDto;
import com.trablock.web.entity.location.LocationType;
import com.trablock.web.repository.location.LocationRepository;
import com.trablock.web.repository.location.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    private final TypeRepository typeRepository;

    @Override
    public Long create(LocationDto locationDto) {

        return 0L;
    }

    @Override
    public LocationDto findLocation(Long locationId) {
        return null;
    }

    @Override
    public LocationDto findLocationByName(String locationName) {
        return null;
    }

    @Override
    public List<LocationDto> findAllLocationByMember(Long memberId) {
        return null;
    }

    @Override
    public List<LocationDto> findAllPublicLocationByMember(Long memberId) {
        return null;
    }

    @Override
    public List<LocationDto> findAllLocationByType(LocationType type) {
        return null;
    }
}

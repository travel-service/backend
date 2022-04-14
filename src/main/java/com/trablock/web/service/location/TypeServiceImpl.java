package com.trablock.web.service.location;

import com.trablock.web.dto.location.LocationDto;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.location.LocationType;
import com.trablock.web.entity.location.type.*;
import com.trablock.web.repository.location.LocationRepository;
import com.trablock.web.repository.location.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;

    private final LocationRepository locationRepository;

    public LocationDto getWithType() {
        return null;
    }

    @Override
    public Attraction getAttraction(Long locationId) {
        return typeRepository.findAttractionByLocationId(locationId).get();
    }

    @Override
    public Restaurant getRestaurant(Long locationId) {
        return typeRepository.findRestaurantByLocationId(locationId).get();
    }

    @Override
    public Culture getCulture(Long locationId) {
        return typeRepository.findCultureByLocationId(locationId).get();
    }

    @Override
    public Festival getFestival(Long locationId) {
        return typeRepository.findFestivalByLocationId(locationId).get();
    }

    @Override
    public Lodge getLodge(Long locationId) {
        return typeRepository.findLodgeByLocationId(locationId).get();
    }

    @Override
    public Leports getLeports(Long locationId) {
        return typeRepository.findLeportsByLocationId(locationId).get();
    }

    @Override
    public LocationType checkType(LocationDto locationDto) {
        Optional<Location> findLocation = locationRepository.findById(locationDto.getId());

        if (findLocation.isPresent()) {
            Location loc = findLocation.get();
        }

        return locationDto.getType();
    }
}

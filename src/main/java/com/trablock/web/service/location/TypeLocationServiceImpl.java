package com.trablock.web.service.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.repository.location.TypeLocationRepository;
import com.trablock.web.repository.location.type.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class TypeLocationServiceImpl implements TypeLocationService {

    private final TypeLocationRepository typeLocationRepository;
    private final AttractionRepository attractionRepository;
    private final CultureRepository cultureRepository;
    private final FestivalRepository festivalRepository;
    private final LeportsRepository leportsRepository;
    private final LodgeRepository lodgeRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public Object getLocationDetails(Long locationId, LocationType type) {
        switch (type) {
            case ATTRACTION:
                return typeLocationRepository.findAttractionByLocationId(locationId);
            case CULTURE:
                return typeLocationRepository.findCultureByLocationId(locationId);
            case FESTIVAL:
                return typeLocationRepository.findFestivalByLocationId(locationId);
            case LEPORTS:
                return typeLocationRepository.findLeportsByLocationId(locationId);
            case LODGE:
                return typeLocationRepository.findLodgeByLocationId(locationId);
            case RESTAURANT:
                return typeLocationRepository.findRestaurantByLocationId(locationId);
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}

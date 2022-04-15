package com.trablock.web.service.location;

import com.trablock.web.dto.location.LocationDto;
import com.trablock.web.dto.location.TypeDto;
import com.trablock.web.entity.location.LocationType;
import com.trablock.web.entity.location.type.*;

import static com.trablock.web.dto.location.TypeDto.*;

public interface TypeService {

    attractionDto toAttractionDto(Long locationId);

    restaurantDto toRestaurantDto(Long locationId);

    cultureDto toCultureDto(Long locationId);

    festivalDto toFestivalDto(Long locationId);

    lodgeDto toLodgeDto(Long locationId);

    leportsDto toLeportsDto(Long locationId);
}

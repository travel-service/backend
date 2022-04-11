package com.trablock.web.service.location;

import com.trablock.web.dto.LocationDto;
import com.trablock.web.entity.location.LocationType;
import com.trablock.web.entity.location.type.*;

public interface TypeService {


    LocationType checkType(LocationDto locationDto);

    Attraction getAttraction(Long locationId);

    Restaurant getRestaurant(Long locationId);

    Culture getCulture(Long locationId);

    Festival getFestival(Long locationId);

    Lodge getLodge(Long locationId);

    Leports getLeports(Long locationId);

}

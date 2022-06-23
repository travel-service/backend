package com.trablock.web.repository.location;

import com.trablock.web.dto.location.*;

public interface LocationRepositoryCustom {

    AttractionDto findAttractionByLocationId(Long locationId);

    CultureDto findCultureByLocationId(Long locationId);

    FestivalDto findFestivalByLocationId(Long locationId);

    LeportsDto findLeportByLocationId(Long locationId);

    LodgeDto findLodgeByLocationId(Long locationId);

    RestaurantDto findRestaurantByLocationId(Long locationId);

}

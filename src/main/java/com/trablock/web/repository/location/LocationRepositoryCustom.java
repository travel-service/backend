package com.trablock.web.repository.location;

import com.trablock.web.dto.location.*;
import com.trablock.web.entity.location.type.*;

public interface LocationRepositoryCustom {

    Long saveAttraction(Attraction attraction);

    Long saveCulture(Culture culture);

    Long saveLeports(Leports leports);

    Long saveLodge(Lodge lodge);

    Long saveFestival(Festival festival);

    Long saveRestaurant(Restaurant restaurant);

    AttractionDto findAttractionByLocationId(Long locationId);

    CultureDto findCultureByLocationId(Long locationId);

    FestivalDto findFestivalByLocationId(Long locationId);

    LeportsDto findLeportByLocationId(Long locationId);

    LodgeDto findLodgeByLocationId(Long locationId);

    RestaurantDto findRestaurantByLocationId(Long locationId);

    void deleteAttraction(Attraction attraction);

    void deleteCulture(Culture culture);

    void deleteLeports(Leports leports);

    void deleteLodge(Lodge lodge);

    void deleteFestival(Festival festival);

    void deleteRestaurant(Restaurant restaurant);

}

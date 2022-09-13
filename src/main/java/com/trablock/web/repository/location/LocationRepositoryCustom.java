package com.trablock.web.repository.location;

import com.trablock.web.dto.location.BlockLocationView;
import com.trablock.web.dto.location.type.*;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.location.type.*;
import com.trablock.web.entity.member.Member;

import java.util.List;

public interface LocationRepositoryCustom {

    List<Location> findLocationsByMemberId(Long memberId);

    List<BlockLocationView> findSelectedLocationByPlanId(Long planId);

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

    List<AttractionDto> findAttractionsByLocationIds(List<Long> locationIds);

    List<CultureDto> findCulturesByLocationIds(List<Long> locationIds);

    List<FestivalDto> findFestivalsByLocationIds(List<Long> locationIds);

    List<LeportsDto> findLeportsByLocationIds(List<Long> locationIds);

    List<LodgeDto> findLodgesByLocationIds(List<Long> locationIds);

    List<RestaurantDto> findRestaurantsByLocationIds(List<Long> locationIds);

}

package com.trablock.web.service.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.dto.location.*;
import com.trablock.web.entity.location.Location;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public interface LocationService {

    Long createLocationByMember(LocationWrapperDto wrapperDto);

    Object getLocationDetails(Long locationId, LocationType locationType);

    boolean saveTypeLocation(TypeLocationRequestDto requestDto, LocationType type);

    boolean deleteLocationByMember(Long locationId);

    boolean updateLocationByMember(LocationWrapperDto wrapperDto, Long locationId);

    HashMap<String, Object> getMarkLocationList();

    HashMap<String, Object> getBlockLocationList();

    List<Location> getLocationListWithLocationIds(List<Long> locationIds);

    HashSet<MarkLocationView> getMarkLocationListWithType(LocationType type);

    HashSet<BlockLocationView> getBlockLocationListWithType(LocationType type);

}

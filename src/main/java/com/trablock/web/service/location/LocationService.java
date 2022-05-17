package com.trablock.web.service.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.dto.location.LocationDto;
import com.trablock.web.dto.location.LocationSaveRequestDto;
import com.trablock.web.dto.location.SimpleLocationDto;
import com.trablock.web.entity.location.Location;

import java.util.HashMap;
import java.util.List;


public interface LocationService {

    Long create(LocationSaveRequestDto locationDto);

    List<SimpleLocationDto> getSimpleLocations();

    SimpleLocationDto toSimpleDto(Location location);

    List<LocationDto> getMemberLocations(Long memberId);

    List<LocationDto> getPublicMemberLocation(Long memberId);

    HashMap<String, Object> viewSimpleAll();

    List<SimpleLocationDto> viewSimple(LocationType type);

    LocationDto getLocationDetails(Long locationId);
}

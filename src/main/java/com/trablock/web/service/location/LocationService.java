package com.trablock.web.service.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.dto.location.LocationDto;
import com.trablock.web.dto.location.LocationSaveRequestDto;
import com.trablock.web.dto.location.MarkLocationDto;
import com.trablock.web.entity.location.Location;

import java.util.HashMap;
import java.util.List;


public interface LocationService {

    Long create(LocationSaveRequestDto locationDto);

    List<MarkLocationDto> getMarkLocationDtos();

    MarkLocationDto toMarkLocationDto(Location location);

    List<LocationDto> getMemberLocations(Long memberId);

    List<LocationDto> getPublicMemberLocation(Long memberId);

    List<MarkLocationDto> getMarkLocationsWithType(LocationType type);

    LocationDto getLocationDetails(Long locationId);
}

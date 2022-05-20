package com.trablock.web.service.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.dto.location.*;
import com.trablock.web.entity.location.Location;

import java.util.List;


public interface LocationService {

    LocationDto createLocation(LocationSaveRequestDto locationDto);

    MemberLocationDto createMemberLocation(MemberLocationSaveRequestDto memberLocationSaveDto);

    List<MarkLocationDto> getMarkLocationDtos();

    MarkLocationDto toMarkLocationDto(Location location);

    List<LocationDto> getMemberLocations(Long memberId);

    List<LocationDto> getPublicMemberLocation(Long memberId);

    List<MarkLocationDto> getMarkLocationsWithType(LocationType type);

    LocationDto getLocationDetails(Long locationId);
}

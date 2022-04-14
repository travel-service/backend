package com.trablock.web.service.location;

import com.trablock.web.dto.location.LocationDto;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.location.LocationType;

import java.util.List;

import static com.trablock.web.entity.location.Location.*;

public interface LocationService {

    Long create(LocationSaveRequestDto locationDto);

    LocationDto toDto(Long locationId);

    LocationDto toDto(String locationName);

    Location toEntity(LocationDto locationDto);

    List<LocationDto> getMemberLocations(Long memberId);

    List<LocationDto> getPublicMemberLocation(Long memberId);

    List<LocationDto> getLocationWithType(LocationType type);
}

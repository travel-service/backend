package com.trablock.web.service.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.dto.location.*;
import com.trablock.web.entity.location.Location;

import java.util.List;


public interface LocationService {

    LocationDto createLocation(LocationSaveRequestDto locationDto);

    MemberLocationDto createMemberLocation(MemberLocationSaveRequestDto memberLocationSaveDto);

    List<MarkLocationDto> getMarkLocationList();

    List<LocationDto> getMemberLocationList(Long memberId);

    List<LocationDto> getPublicMemberLocationList(Long memberId);

    List<MarkLocationDto> getMarkLocationListWithType(LocationType type);

    List<BlockLocatioDto> getBlockLocationListWithType(LocationType type);

    LocationDto getLocationDetails(Long locationId);

    MarkLocationDto toMarkLocationDto(Location location);

    BlockLocatioDto toBlockLocationDto(Location location);
}

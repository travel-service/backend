package com.trablock.web.service.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.dto.location.*;
import com.trablock.web.entity.location.Location;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public interface LocationService {

    LocationDto createLocation(LocationSaveRequestDto locationDto);

    MemberLocationDto createMemberLocation(MemberLocationSaveRequestDto memberLocationSaveDto);

    HashMap<String, Object> getMarkLocationList();

    HashMap<String, Object> getBlockLocationList();

    List<LocationDto> getMemberLocationList(Long memberId);

    List<LocationDto> getPublicMemberLocationList(Long memberId);

    HashSet<MarkLocationDto> getMarkLocationListWithType(LocationType type);

    HashSet<BlockLocationDto> getBlockLocationListWithType(LocationType type);

    LocationDto getLocationDetails(Long locationId);

    MarkLocationDto toMarkLocationDto(Location location);

    BlockLocationDto toBlockLocationDto(Location location);
}

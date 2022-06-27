package com.trablock.web.service.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.dto.location.*;
import com.trablock.web.dto.location.save.MemberLocationRequestDto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public interface LocationService {

    Object createLocationByMember(LocationSaveWrapperDto wrapperDto);

    Object getLocationDetails(Long locationId, LocationType locationType);

    void saveTypeLocation(TypeLocationRequestDto requestDto, LocationType type);

    HashMap<String, Object> getMarkLocationList();

    HashMap<String, Object> getBlockLocationList();

    List<LocationDto> getMemberLocationList(Long memberId);

    List<LocationDto> getPublicMemberLocationList(Long memberId);

    HashSet<MarkLocationView> getMarkLocationListWithType(LocationType type);

    HashSet<BlockLocationView> getBlockLocationListWithType(LocationType type);

}

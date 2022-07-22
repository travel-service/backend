package com.trablock.web.service.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.dto.location.*;
import com.trablock.web.dto.location.save.InformationRequestDto;
import com.trablock.web.dto.location.save.MemberLocationRequestDto;
import com.trablock.web.dto.location.type.TypeLocationDto;
import com.trablock.web.entity.location.Location;

import java.util.HashSet;
import java.util.List;
import java.util.Map;


public interface LocationService {

    TypeLocationDto getLocationDetails(Long locationId, LocationType locationType);

    Long createLocationByMember(LocationWrapperDto wrapperDto);

    boolean saveTypeLocation(TypeLocationRequestDto requestDto, LocationType type);

    boolean updateLocationInformation(InformationRequestDto informationDto, Long locationId);

    boolean updateMemberLocation(MemberLocationRequestDto memberLocationDto, Long locationId);

    boolean deleteLocationByMember(Long locationId);

    boolean updateLocationByMember(LocationWrapperDto wrapperDto, Long locationId);

    Map<String, List<BlockLocationDto>> getBlockLocationListFromLocationList(List<Location> locationList);

    Map<String, List<MarkLocationDto>> getMarkLocationListFromLocationList(List<Location> locationList);

    List<MarkLocationDto> toMarkLocationDtoList(List<Location> locationList);

    List<BlockLocationDto> toBlockLocationDtoList(List<Location> locationList);

    Map<String, List<BlockLocationDto>> classifyBlockLocationListWithType(List<BlockLocationDto> blockLocationDtoListList);

    Map<String, List<MarkLocationDto>> classifyMarkLocationDtoList(List<MarkLocationDto> markLocationDtoListList);

    Map<String, List<MarkLocationDto>> getMarkLocationList();

    Map<String, List<BlockLocationDto>> getBlockLocationList();

    List<Location> getLocationListWithLocationIds(List<Long> locationIds);

    HashSet<MarkLocationView> getMarkLocationListWithType(LocationType type);

    HashSet<BlockLocationView> getBlockLocationListWithType(LocationType type);

    MemberLocationListDto getMemberLocationList(Long memberId);
}

package com.trablock.web.service.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.dto.location.*;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.location.MemberLocation;
import com.trablock.web.repository.location.LocationRepository;
import com.trablock.web.repository.location.MemberLocationRepository;
import com.trablock.web.service.location.mapper.LocationMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.trablock.web.domain.LocationType.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final MemberLocationRepository memberLocationRepository;
    private final LocationMapper locationMapper = Mappers.getMapper(LocationMapper.class);

    @Override
    @Transactional
    public LocationDto createLocation(LocationSaveRequestDto requestDto) {
        Location save = locationRepository.save(requestDto.toEntity());
        return getLocationDetails(save.getId(), requestDto.getType());
    }

    /**
     * ID 값 반환이 아니라 DTO를 반환한다면?
     * 굳이 DTO를 또 찾을 필요가 없지 않을까.
     *
     * @param memberLocationSaveDto
     * @return
     */
    @Override
    public MemberLocationDto createMemberLocation(MemberLocationSaveRequestDto memberLocationSaveDto) {
        MemberLocation save = memberLocationRepository.save(memberLocationSaveDto.toEntity());
        return save.toDto();
    }

    @Override
    public LocationDto getLocationDetails(Long locationId, LocationType locationType) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Optional<Location> locationById = locationRepository.findLocationById(locationId);
        return locationById.map(locationMapper::toDto).orElse(null);
    }

    @Override
    public HashMap<String, Object> getMarkLocationList() {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("Lodge", getMarkLocationListWithType(LODGE));
        map.put("Restaurant", getMarkLocationListWithType(RESTAURANT));
        map.put("Attraction", getMarkLocationListWithType(ATTRACTION));
        map.put("Culture", getMarkLocationListWithType(CULTURE));
        map.put("Festival", getMarkLocationListWithType(FESTIVAL));
        map.put("Leports", getMarkLocationListWithType(LEPORTS));

        return map;
    }

    @Override
    public HashMap<String, Object> getBlockLocationList() {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("Lodge", getBlockLocationListWithType(LODGE));
        map.put("Restaurant", getBlockLocationListWithType(RESTAURANT));
        map.put("Attraction", getBlockLocationListWithType(ATTRACTION));
        map.put("Culture", getBlockLocationListWithType(CULTURE));
        map.put("Festival", getBlockLocationListWithType(FESTIVAL));
        map.put("Leports", getBlockLocationListWithType(LEPORTS));

        return map;
    }


    @Override
    public List<LocationDto> getMemberLocationList(Long memberId) {

        for (MemberLocation memberLocation : memberLocationRepository.findAllByMemberId(memberId)) {
            Optional<Location> byId = locationRepository.findById(memberLocation.getLocationId());
            byId.ifPresent(location -> ((List<LocationDto>) new ArrayList<LocationDto>()).add(locationMapper.toDto(location)));
        }
        return new ArrayList<LocationDto>();
    }


    @Override
    public List<LocationDto> getPublicMemberLocationList(Long memberId) {

        for (MemberLocation memberLocation : memberLocationRepository.findAllByMemberIdAndIsPublicTrue(memberId)) {
            Optional<Location> byId = locationRepository.findById(memberLocation.getLocationId());
            byId.ifPresent(location -> ((List<LocationDto>) new ArrayList<LocationDto>()).add(locationMapper.toDto(location)));
        }
        return new ArrayList<LocationDto>();
    }

    @Override
    public HashSet<MarkLocationDto> getMarkLocationListWithType(LocationType type) {
        return locationRepository.findAllByTypeAndIsMemberFalse(type, MarkLocationDto.class);
    }

    @Override
    public HashSet<BlockLocationDto> getBlockLocationListWithType(LocationType type) {
        return locationRepository.findAllByTypeAndIsMemberFalse(type, BlockLocationDto.class);
    }

}

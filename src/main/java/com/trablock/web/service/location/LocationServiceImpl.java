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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return getLocationDetails(save.getId());

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
    public LocationDto getLocationDetails(Long locationId) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Optional<Location> locationById = locationRepository.findLocationById(locationId);
        return locationById.map(locationMapper::toDto).orElse(null);
    }

    @Override
    public List<MarkLocationDto> getMarkLocationList() {
        List<Location> locations = locationRepository.findAllByIsMemberFalse();
        return locations.stream().map(this::toMarkLocationDto)
                .collect(Collectors.toList());
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
    public List<MarkLocationDto> getMarkLocationListWithType(LocationType type) {
        List<MarkLocationDto> markLocationDtos = new ArrayList<MarkLocationDto>();
        List<Location> locations = locationRepository.findAllByTypeAndIsMemberFalse(type);
        locations.forEach(location -> markLocationDtos.add(toMarkLocationDto(location)));
        return markLocationDtos;
    }

    @Override
    public List<BlockLocationDto> getBlockLocationListWithType(LocationType type) {
        List<BlockLocationDto> blockLocationDtoList = new ArrayList<BlockLocationDto>();
        List<Location> locations = locationRepository.findAllByTypeAndIsMemberFalse(type);
        locations.forEach(location -> blockLocationDtoList.add(toBlockLocationDto(location)));
        return blockLocationDtoList;
    }

    @Override
    public MarkLocationDto toMarkLocationDto(Location location) {
        return MarkLocationDto.builder()
                .id(location.getId())
                .name(location.getName())
                .type(location.getType())
                .coords(location.getCoords())
                .build();
    }

    @Override
    public BlockLocationDto toBlockLocationDto(Location location) {
        return BlockLocationDto.builder()
                .id(location.getId())
                .name(location.getName())
                .address1(location.getAddress1())
                .address2(location.getAddress2())
                .image(location.getImage())
                .type(location.getType())
                .build();
    }

}

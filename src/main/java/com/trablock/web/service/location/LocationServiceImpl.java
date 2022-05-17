package com.trablock.web.service.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.dto.location.LocationDto;
import com.trablock.web.dto.location.LocationSaveRequestDto;
import com.trablock.web.dto.location.MarkLocationDto;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.location.MemberLocation;
import com.trablock.web.repository.location.LocationRepository;
import com.trablock.web.repository.location.MemberLocationRepository;
import com.trablock.web.repository.location.TypeLocationRepository;
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

import static com.trablock.web.domain.LocationType.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final MemberLocationRepository memberLocationRepository;
    private final LocationMapper locationMapper = Mappers.getMapper(LocationMapper.class);
    private final TypeLocationRepository typeRepository = new TypeLocationRepository();

    @Override
    @Transactional
    public Long create(LocationSaveRequestDto requestDto) {
        return locationRepository.save(requestDto.toEntity()).getId();
    }

    @Override
    public LocationDto getLocationDetails(Long locationId) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Optional<Location> locationById = locationRepository.findLocationById(locationId);
        return locationById.map(locationMapper::toDto).orElse(null);
    }

    // MarkLocationDto or PointLocationDto
    @Override
    public List<MarkLocationDto> getMarkLocationDtos() {
        List<Location> locations = locationRepository.findAll();
        return locations.stream().map(this::toMarkLocationDto)
                .collect(Collectors.toList());
    }

    @Override
    public MarkLocationDto toMarkLocationDto(Location location) {
        MarkLocationDto markLocationDto = MarkLocationDto.builder()
                .id(location.getId())
                .name(location.getName())
                .type(location.getType())
                .coords(location.getCoords())
                .build();

        return markLocationDto;
    }

    @Override
    public List<LocationDto> getMemberLocations(Long memberId) {

        for (MemberLocation memberLocation : memberLocationRepository.findAllByMemberId(memberId)) {
            Optional<Location> byId = locationRepository.findById(memberLocation.getLocationId());
            byId.ifPresent(location -> ((List<LocationDto>) new ArrayList<LocationDto>()).add(locationMapper.toDto(location)));
        }
        return new ArrayList<LocationDto>();
    }

    @Override
    public List<LocationDto> getPublicMemberLocation(Long memberId) {

        for (MemberLocation memberLocation : memberLocationRepository.findAllByMemberIdAndIsPublicTrue(memberId)) {
            Optional<Location> byId = locationRepository.findById(memberLocation.getLocationId());
            byId.ifPresent(location -> ((List<LocationDto>) new ArrayList<LocationDto>()).add(locationMapper.toDto(location)));
        }
        return new ArrayList<LocationDto>();
    }

    @Override
    public List<MarkLocationDto> getMarkLocationsWithType(LocationType type) {
        List<MarkLocationDto> markLocationDtos = new ArrayList<MarkLocationDto>();
        List<Location> locations = locationRepository.findAllByType(type);
        locations.forEach(location -> markLocationDtos.add(toMarkLocationDto(location)));
        return markLocationDtos;
    }
}

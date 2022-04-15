package com.trablock.web.service.location;

import com.trablock.web.dto.location.LocationDto;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.location.LocationType;
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
import java.util.List;
import java.util.Optional;

import static com.trablock.web.entity.location.Location.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final MemberLocationRepository memberLocationRepository;
    private final LocationMapper locationMapper = Mappers.getMapper(LocationMapper.class);

    @Override
    @Transactional
    public Long create(LocationSaveRequestDto requestDto) {
        return locationRepository.save(requestDto.toEntity()).getId();
    }

    @Override
    public List<SimpleLocDto> toSimpleDto() {
        for (Location location : locationRepository.findAll()) {
            SimpleLocDto simpleLocDto = SimpleLocDto.builder()
                    .name(location.getName())
                    .coords(location.getCoords())
                    .type(location.getType())
                    .build();
            ((List<SimpleLocDto>) new ArrayList<SimpleLocDto>()).add(simpleLocDto);
        }
        return new ArrayList<SimpleLocDto>();
    }

    @Override
    public LocationDto toDto(Long locationId) {
        Optional<Location> location = locationRepository.findById(locationId);

        return location.map(locationMapper::toDto).orElse(null);
    }

    @Override
    public LocationDto toDto(String locationName) {
        Optional<Location> location = locationRepository.findByName(locationName);

        return location.map(locationMapper::toDto).orElse(null);
    }

    @Override
    public Location toEntity(LocationDto locationDto) {
        return locationMapper.toEntity(locationDto);
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
    public List<SimpleLocDto> viewSimple(LocationType type) {

        for (Location location : locationRepository.findLocationByType(type)) {
            ((List<SimpleLocDto>) new ArrayList<SimpleLocDto>()).add(SimpleLocDto.builder()
                    .locationId(location.getId())
                    .name(location.getName())
                    .coords(location.getCoords())
                    .type(location.getType())
                    .build());
        }
        return new ArrayList<SimpleLocDto>();
    }
}

package com.trablock.web.service.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.dto.location.LocationDto;
import com.trablock.web.dto.location.LocationSaveRequestDto;
import com.trablock.web.dto.location.SimpleLocationDto;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public List<SimpleLocationDto> getSimpleLocations() {
        List<Location> locations = locationRepository.findAll();
        return locations.stream().map(this::toSimpleDto)
                .collect(Collectors.toList());
    }

    @Override
    public SimpleLocationDto toSimpleDto(Location location) {
        SimpleLocationDto simpleDto = SimpleLocationDto.builder()
                .id(location.getId())
                .name(location.getName())
                .type(location.getType())
                .coords(location.getCoords())
                .build();

        return simpleDto;
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
    public HashMap<String, Object> viewSimpleAll() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("Lodge", viewSimple(LODGE));
        map.put("Restaurant", viewSimple(RESTAURANT));
        map.put("Attraction", viewSimple(ATTRACTION));
        map.put("Leports", viewSimple(LEPORTS));
        map.put("Festival", viewSimple(FESTIVAL));
        map.put("Culture", viewSimple(CULTURE));

        return map;
    }

    @Override
    public List<SimpleLocationDto> viewSimple(LocationType type) {

        for (Location location : locationRepository.findLocationByType(type)) {
            ((List<SimpleLocationDto>) new ArrayList<SimpleLocationDto>()).add(SimpleLocationDto.builder()
                    .id(location.getId())
                    .name(location.getName())
                    .coords(location.getCoords())
                    .type(location.getType())
                    .build());
        }
        return new ArrayList<SimpleLocationDto>();
    }
}

package com.trablock.web.service.location;

import com.trablock.web.dto.location.LocationDto;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.location.LocationType;
import com.trablock.web.entity.location.MemberLocation;
import com.trablock.web.repository.location.LocationRepository;
import com.trablock.web.repository.location.MemberLocationRepository;
import com.trablock.web.repository.location.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.trablock.web.entity.location.Location.*;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final MemberLocationRepository memberLocationRepository;
    private final TypeRepository typeRepository;
    private final LocationMapper locationMapper = Mappers.getMapper(LocationMapper.class);

    @Override
    public Long create(LocationSaveRequestDto requestDto) {
        return locationRepository.save(requestDto.toEntity()).getId();
    }

    @Override
    public LocationDto toDto(Long locationId) {
        Location location = locationRepository.findById(locationId).get();
        return locationMapper.toDto(location);
    }

    @Override
    public LocationDto toDto(String locationName) {
        Location location = locationRepository.findByName(locationName).get();
        return locationMapper.toDto(location);
    }

    @Override
    public Location toEntity(LocationDto locationDto) {
        return locationMapper.toEntity(locationDto);
    }

    @Override
    public List<LocationDto> getMemberLocations(Long memberId) {
        List<MemberLocation> locByMemberId = memberLocationRepository.findAllByMemberId(memberId);

        List<LocationDto> locationDtoList = new ArrayList<LocationDto>();
        for (MemberLocation memberLocation : locByMemberId) {
            Optional<Location> byId = locationRepository.findById(memberLocation.getLocationId());
            byId.ifPresent(location -> locationDtoList.add(locationMapper.toDto(location)));
        }
        return locationDtoList;
    }

    @Override
    public List<LocationDto> getPublicMemberLocation(Long memberId) {
        List<MemberLocation> locByMemberId = memberLocationRepository.findAllByMemberIdAndIsPublicTrue(memberId);

        List<LocationDto> locationDtoList = new ArrayList<LocationDto>();
        for (MemberLocation memberLocation : locByMemberId) {
            Optional<Location> byId = locationRepository.findById(memberLocation.getLocationId());
            byId.ifPresent(location -> locationDtoList.add(locationMapper.toDto(location)));
        }
        return locationDtoList;
    }

    @Override
    public List<LocationDto> getLocationWithType(LocationType type) {
        locationRepository.findLocationByType(type);
        return null;
    }
}

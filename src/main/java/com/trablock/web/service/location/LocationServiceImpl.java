package com.trablock.web.service.location;

import com.trablock.web.controller.exception.MemberHasNoneOwnershipException;
import com.trablock.web.domain.LocationType;
import com.trablock.web.dto.location.*;
import com.trablock.web.dto.location.save.InformationRequestDto;
import com.trablock.web.dto.location.save.MemberLocationRequestDto;
import com.trablock.web.dto.location.type.TypeLocationDto;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.location.MemberLocation;
import com.trablock.web.entity.location.type.*;
import com.trablock.web.repository.location.InformationRepository;
import com.trablock.web.repository.location.LocationRepository;
import com.trablock.web.repository.location.MemberLocationRepository;
import com.trablock.web.service.location.mapper.TypeLocationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final MemberLocationRepository memberLocationRepository;
    private final InformationRepository informationRepository;
    private final TypeLocationMapper typeLocationMapper;

    @Override
    @Transactional
    public synchronized Long createLocationByMember(LocationWrapperDto wrapperDto) {
        Location location = locationRepository.save(wrapperDto.getLocation().toEntity());
        Long locationId = location.getId();
        LocationType type = location.getType();

        TypeLocationRequestDto typeLocationRequestDto = wrapperDto.getTypeLocation();
        typeLocationRequestDto.setLocationId(locationId);

        updateLocationInformation(wrapperDto.getInformation(), locationId);
        updateMemberLocation(wrapperDto.getMemberLocation(), locationId);
        saveTypeLocation(typeLocationRequestDto, type);

        return locationId;
    }

    @Override
    @Transactional
    public boolean deleteLocationByMember(Long locationId, Long memberId) {
        MemberLocation memberLocation = memberLocationRepository.findByLocationId(locationId).orElseThrow();
        if (verifyLocationOwnership(memberId, memberLocation)) {
            memberLocationRepository.deleteMemberLocationByLocationId(locationId);
            return !memberLocationRepository.existsMemberLocationByLocationId(locationId);
        }
        throw new NoSuchElementException("장소를 찾을 수 없어요!");
    }

    @Override
    @Transactional
    public boolean updateLocationByMember(LocationWrapperDto wrapperDto, Long locationId, Long memberId) throws MemberHasNoneOwnershipException {
        MemberLocation memberLocation = memberLocationRepository.findByLocationId(locationId).orElseThrow();
        if (verifyLocationOwnership(memberId, memberLocation)) {
            updateLocationInformation(wrapperDto.getInformation(), locationId);
            updateMemberLocation(wrapperDto.getMemberLocation(), locationId);
            return true;
        }
        throw new MemberHasNoneOwnershipException("권한이 없습니다.");
    }

    @Override
    public List<Location> getLocationListWithLocationIds(List<Long> locationIdList) {
        return locationRepository.findAllLocationByIdIn(locationIdList);
    }

    @Override
    public Map<String, List<BlockLocationDto>> getBlockLocationListFromLocationList(List<Location> locationList) {
        List<Long> locationIds = locationList.stream().map(Location::getId).collect(Collectors.toList());
        List<BlockLocationDto> blockLocationDtoList = getBlockLocationsFromTypeLocations(getTypeLocationDtoListByLocationIds(locationIds));
        return classifyBlockLocationListWithType(blockLocationDtoList);
    }

    @Override
    public Map<String, List<MarkLocationDto>> getMarkLocationListFromLocationList(List<Location> locationList) {
        List<MarkLocationDto> markLocationDtoList = toMarkLocationDtoList(locationList);
        return classifyMarkLocationDtoList(markLocationDtoList);
    }

    @Override
    public List<MarkLocationDto> toMarkLocationDtoList(List<Location> locationList) {
        return locationList.stream().map(Location::toMarkLocationDto).collect(Collectors.toList());
    }

    @Override
    public List<BlockLocationDto> toBlockLocationDtoList(List<Location> locationList) {
        return locationList.stream().map(Location::toBlockLocationDto).collect(Collectors.toList());
    }

    @Override
    public Map<String, List<BlockLocationDto>> classifyBlockLocationListWithType(List<BlockLocationDto> blockLocationDtoList) {
        return blockLocationDtoList.stream().collect(Collectors
                .groupingBy(blockLocationDto -> blockLocationDto.getType().getType()));
    }

    @Override
    public Map<String, List<MarkLocationDto>> classifyMarkLocationDtoList(List<MarkLocationDto> markLocationDtoListList) {
        return markLocationDtoListList.stream().collect(Collectors
                .groupingBy(markLocationDto -> markLocationDto.getType().getType()));
    }

    @Override
    @Transactional
    public boolean saveTypeLocation(TypeLocationRequestDto requestDto, LocationType locationType) throws NoSuchElementException {
        switch (locationType) {
            case ATTRACTION:
                Attraction attraction = typeLocationMapper.getAttractionMapper().toEntity(requestDto);
                locationRepository.saveAttraction(attraction);
                return true;
            case CULTURE:
                Culture culture = typeLocationMapper.getCultureMapper().toEntity(requestDto);
                locationRepository.saveCulture(culture);
                return true;
            case FESTIVAL:
                Festival festival = typeLocationMapper.getFestivalMapper().toEntity(requestDto);
                locationRepository.saveFestival(festival);
                return true;
            case LEPORTS:
                Leports leports = typeLocationMapper.getLeportsMapper().toEntity(requestDto);
                locationRepository.saveLeports(leports);
                return true;
            case LODGE:
                Lodge lodge = typeLocationMapper.getLodgeMapper().toEntity(requestDto);
                locationRepository.saveLodge(lodge);
                return true;
            case RESTAURANT:
                Restaurant restaurant = typeLocationMapper.getRestaurantMapper().toEntity(requestDto);
                locationRepository.saveRestaurant(restaurant);
                return true;
        }
        throw new NoSuchElementException("설정할 수 없는 타입이에요!");
    }

    @Override
    public TypeLocationDto getLocationDetails(Long locationId, LocationType locationType) throws NoSuchElementException {
        switch (locationType) {
            case ATTRACTION:
                return locationRepository.findAttractionByLocationId(locationId);
            case CULTURE:
                return locationRepository.findCultureByLocationId(locationId);
            case FESTIVAL:
                return locationRepository.findFestivalByLocationId(locationId);
            case LEPORTS:
                return locationRepository.findLeportByLocationId(locationId);
            case LODGE:
                return locationRepository.findLodgeByLocationId(locationId);
            case RESTAURANT:
                return locationRepository.findRestaurantByLocationId(locationId);
        }
        throw new NoSuchElementException("해당 타입의 관광지가 없어요!");
    }

    public BlockLocationDto detailsToBlockLocation(TypeLocationDto typeLocationDto) {
        return typeLocationDto.toBlockLocationDto();
    }

    @Override
    public MarkAndBlockLocationListDto getMarkAndBlockLocationsFromLocationIds(List<Long> locationIds) {
        List<Location> locations = getLocationListWithLocationIds(locationIds);


        return new MarkAndBlockLocationListDto(getMarkLocationListFromLocationList(locations),
                getBlockLocationListFromLocationList(locations));
    }

    @Override
    public Map<String, List<MarkLocationDto>> getMarkLocationList() { // 반환 타입이 Object인 것이 맘에 들지 않는다.
        List<Location> locationList = locationRepository.findAllByIsMemberFalse();
        return getMarkLocationListFromLocationList(locationList);
    }

    @Override
    public Map<String, List<BlockLocationDto>> getBlockLocationList() {
        List<Location> locationList = locationRepository.findAllByIsMemberFalse();
        return getBlockLocationListFromLocationList(locationList);
    }

    @Override
    public HashSet<MarkLocationView> getMarkLocationListWithType(LocationType type) {
        return locationRepository.findAllByTypeAndIsMemberFalse(type, MarkLocationView.class);
    }

    @Override
    public HashSet<BlockLocationView> getBlockLocationListWithType(LocationType type) {
        return locationRepository.findAllByTypeAndIsMemberFalse(type, BlockLocationView.class);
    }

    @Override
    public MarkAndBlockLocationListDto getMemberLocationList(Long memberId) {
        List<Location> locations = locationRepository.findLocationsByMemberId(memberId);
        return new MarkAndBlockLocationListDto(getMarkLocationListFromLocationList(locations),
                getBlockLocationListFromLocationList(locations));
    }

    @Override
    @Transactional
    public boolean updateLocationInformation(InformationRequestDto informationDto, Long locationId) {
        return informationRepository.save(informationDto.toEntity(locationId)).getLocationId().equals(locationId);
    }

    @Override
    @Transactional
    public boolean updateMemberLocation(MemberLocationRequestDto memberLocationDto, Long locationId) {
        return memberLocationRepository.save(memberLocationDto.toEntity(locationId)).getLocationId().equals(locationId);
    }

    @Override
    public boolean verifyLocationOwnership(Long memberId, MemberLocation memberLocation) {
        return memberLocation.getMemberId().equals(memberId);
    }

    @Override
    public List<TypeLocationDto> getTypeLocationDtoListByLocationIds(List<Long> locationIds) {
        List<TypeLocationDto> typeLocationDtoList = new ArrayList<>();
        typeLocationDtoList.addAll(locationRepository.findAttractionsByLocationIds(locationIds));
        typeLocationDtoList.addAll(locationRepository.findCulturesByLocationIds(locationIds));
        typeLocationDtoList.addAll(locationRepository.findFestivalsByLocationIds(locationIds));
        typeLocationDtoList.addAll(locationRepository.findLeportsByLocationIds(locationIds));
        typeLocationDtoList.addAll(locationRepository.findLodgesByLocationIds(locationIds));
        typeLocationDtoList.addAll(locationRepository.findRestaurantsByLocationIds(locationIds));

        return typeLocationDtoList;
    }

    @Override
    public List<BlockLocationDto> getBlockLocationsFromTypeLocations(List<TypeLocationDto> typeLocationDtoList) {
        return typeLocationDtoList.stream().map(TypeLocationDto::toBlockLocationDto).collect(Collectors.toList());
    }

}

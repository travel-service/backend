package com.trablock.web.service.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.dto.location.BlockLocationView;
import com.trablock.web.dto.location.LocationWrapperDto;
import com.trablock.web.dto.location.MarkLocationView;
import com.trablock.web.dto.location.TypeLocationRequestDto;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.location.type.*;
import com.trablock.web.repository.location.InformationRepository;
import com.trablock.web.repository.location.LocationRepository;
import com.trablock.web.repository.location.MemberLocationRepository;
import com.trablock.web.service.location.mapper.TypeLocationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static com.trablock.web.domain.LocationType.*;

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
    public Long createLocationByMember(LocationWrapperDto wrapperDto) {
        Location savedLocation = locationRepository.save(wrapperDto.getLocation().toEntity());
        Long locationId = savedLocation.getId();
        LocationType type = savedLocation.getType();

        informationRepository.save(wrapperDto.getInformation().toEntity(locationId));
        memberLocationRepository.save(wrapperDto.getMemberLocation().toEntity(locationId));

        TypeLocationRequestDto typeLocationRequestDto = wrapperDto.getTypeLocation();
        typeLocationRequestDto.setLocationId(locationId);

        saveTypeLocation(typeLocationRequestDto, type);
        return locationId;
    }

    @Override
    public boolean deleteLocationByMember(Long locationId) {
        memberLocationRepository.deleteMemberLocationByLocationId(locationId);
        return memberLocationRepository.existsMemberLocationByLocationId(locationId);
    }

    @Override
    public boolean updateLocationByMember(LocationWrapperDto wrapperDto, Long locationId) {

        return false;
    }

    @Override
    public List<Location> getLocationListWithLocationIds(List<Long> locationIdList) {
        return locationRepository.findAllLocationByIdIn(locationIdList);
    }

    @Override
    public boolean saveTypeLocation(TypeLocationRequestDto requestDto, LocationType locationType) {
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
        return false;
    }

    @Override
    public Object getLocationDetails(Long locationId, LocationType locationType) {
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
        return null; // 예외처리 조금 더 고민해보자
    }

    @Override
    public HashMap<String, Object> getMarkLocationList() { // 반환 타입이 Object인 것이 맘에 들지 않는다.
        // 부모 클래스 만들어서 묶어버릴까.. 생각중.
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
    public HashSet<MarkLocationView> getMarkLocationListWithType(LocationType type) {
        return locationRepository.findAllByTypeAndIsMemberFalse(type, MarkLocationView.class);
    }

    @Override
    public HashSet<BlockLocationView> getBlockLocationListWithType(LocationType type) {
        return locationRepository.findAllByTypeAndIsMemberFalse(type, BlockLocationView.class);
    }

}

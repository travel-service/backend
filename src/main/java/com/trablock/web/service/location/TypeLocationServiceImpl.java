package com.trablock.web.service.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.dto.location.TypeLocationSaveRequestDto;
import com.trablock.web.entity.location.type.*;
import com.trablock.web.repository.location.CustomTypeRepositoryImpl;
import com.trablock.web.repository.location.type.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class TypeLocationServiceImpl implements TypeLocationService {

    private final CustomTypeRepositoryImpl typeRepositoryImpl;
    private final AttractionRepository attractionRepository;
    private final CultureRepository cultureRepository;
    private final FestivalRepository festivalRepository;
    private final LeportsRepository leportsRepository;
    private final LodgeRepository lodgeRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public Object getLocationDetails(Long locationId, LocationType type) {
        switch (type) {
            case ATTRACTION:
                return typeRepositoryImpl.findAttractionByLocationId(locationId);
            case CULTURE:
                return typeRepositoryImpl.findCultureByLocationId(locationId);
            case FESTIVAL:
                return typeRepositoryImpl.findFestivalByLocationId(locationId);
            case LEPORTS:
                return typeRepositoryImpl.findLeportsByLocationId(locationId);
            case LODGE:
                return typeRepositoryImpl.findLodgeByLocationId(locationId);
            case RESTAURANT:
                return typeRepositoryImpl.findRestaurantByLocationId(locationId);
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    @Override
    public Object createTypeLocation(TypeLocationSaveRequestDto typeLocationSaveRequestDto, Long locationId, LocationType type) {
        switch (type) {
            case ATTRACTION:
                Attraction attraction = Attraction.builder()
                        .locationId(locationId)
                        .parking(typeLocationSaveRequestDto.getParking())
                        .restDate(typeLocationSaveRequestDto.getRestDate())
                        .build();
                typeRepositoryImpl.saveAttraction(attraction);
            case CULTURE:
                Culture culture = Culture.builder()
                        .locationId(locationId)
                        .parking(typeLocationSaveRequestDto.getParking())
                        .restDate(typeLocationSaveRequestDto.getRestDate())
                        .fee(typeLocationSaveRequestDto.getFee())
                        .useTime(typeLocationSaveRequestDto.getUseTime())
                        .spendTime(typeLocationSaveRequestDto.getSpendTime())
                        .build();
                typeRepositoryImpl.saveCulture(culture);
            case FESTIVAL:
                Festival festival = Festival.builder()
                        .locationId(locationId)
                        .endDate(typeLocationSaveRequestDto.getEndDate())
                        .homepage(typeLocationSaveRequestDto.getHomepage())
                        .place(typeLocationSaveRequestDto.getPlace())
                        .placeInfo(typeLocationSaveRequestDto.getPlaceInfo())
                        .playTime(typeLocationSaveRequestDto.getPlayTime())
                        .program(typeLocationSaveRequestDto.getProgram())
                        .fee(typeLocationSaveRequestDto.getFee())
                        .build();
                typeRepositoryImpl.saveFestival(festival);
            case LEPORTS:
                Leports leports = Leports.builder()
                        .locationId(locationId)
                        .openPeriod(typeLocationSaveRequestDto.getOpenPeriod())
                        .parking(typeLocationSaveRequestDto.getParking())
                        .reservation(typeLocationSaveRequestDto.getReservation())
                        .restDate(typeLocationSaveRequestDto.getRestDate())
                        .fee(typeLocationSaveRequestDto.getFee())
                        .useTime(typeLocationSaveRequestDto.getUseTime())
                        .build();
                typeRepositoryImpl.saveLeports(leports);
            case LODGE:
                Lodge lodge = Lodge.builder()
                        .locationId(locationId)
                        .checkInTime(typeLocationSaveRequestDto.getCheckInTime())
                        .checkOutTime(typeLocationSaveRequestDto.getCheckOutTime())
                        .cooking(typeLocationSaveRequestDto.getCooking())
                        .parking(typeLocationSaveRequestDto.getParking())
                        .numOfRooms(typeLocationSaveRequestDto.getNumOfRooms())
                        .reservationUrl(typeLocationSaveRequestDto.getReservationUrl())
                        .subFacility(typeLocationSaveRequestDto.getSubFacility())
                        .build();
                typeRepositoryImpl.saveLodge(lodge);
            case RESTAURANT:
                Restaurant restaurant = Restaurant.builder()
                        .locationId(locationId)
                        .popularMenu(typeLocationSaveRequestDto.getPopularMenu())
                        .openTime(typeLocationSaveRequestDto.getOpenTime())
                        .packing(typeLocationSaveRequestDto.getPacking())
                        .parking(typeLocationSaveRequestDto.getParking())
                        .restDate(typeLocationSaveRequestDto.getRestDate())
                        .menu(typeLocationSaveRequestDto.getMenu())
                        .build();
                typeRepositoryImpl.saveRestaurant(restaurant);
            default:
                new IllegalStateException("Unexpected value: " + type);
        }

        return getLocationDetails(locationId, type);
    }
}

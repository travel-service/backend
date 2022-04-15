package com.trablock.web.service.location;

import com.trablock.web.entity.location.Information;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.location.type.*;
import com.trablock.web.repository.location.InformationRepository;
import com.trablock.web.repository.location.LocationRepository;
import com.trablock.web.repository.location.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.trablock.web.dto.location.TypeDto.*;

@Service
@Transactional
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;

    private final LocationRepository locationRepository;
    private final InformationRepository informationRepository;

    public <T> T viewDetails(Long locationId) {
        Optional<Location> byId = locationRepository.findById(locationId);

        return null;
    }

    @Override
    public attractionDto toAttractionDto(Long locationId) {
        Optional<Attraction> byLocationId = typeRepository.findAttractionByLocationId(locationId);

        if (byLocationId.isPresent()) {
            Attraction attraction = byLocationId.get();

            return attractionDto.builder()
                    .parking(attraction.isParking())
                    .restDate(attraction.getRestDate())
                    .build();
        }

        return attractionDto.builder().build();
    }

    @Override
    public restaurantDto toRestaurantDto(Long locationId) {
        Optional<Restaurant> byLocationId = typeRepository.findRestaurantByLocationId(locationId);

        if (byLocationId.isPresent()) {
            Restaurant restaurant = byLocationId.get();
            return restaurantDto.builder()
                    .popularMenu(restaurant.getPopularMenu())
                    .openTime(restaurant.getOpenTime())
                    .packing(restaurant.isPacking())
                    .parking(restaurant.isParking())
                    .restDate(restaurant.getRestDate())
                    .menu(restaurant.getMenu())
                    .build();
        }
        return restaurantDto.builder().build();
    }

    @Override
    public cultureDto toCultureDto(Long locationId) {
        Optional<Culture> byLocationId = typeRepository.findCultureByLocationId(locationId);

        if (byLocationId.isPresent()) {
            Culture culture = byLocationId.get();

            return cultureDto.builder()
                    .parking(culture.isParking())
                    .restDate(culture.getRestDate())
                    .fee(culture.getFee())
                    .useTime(culture.getUseTime())
                    .spendTime(culture.getSpendTime())
                    .build();
        }

        return cultureDto.builder().build();
    }

    @Override
    public festivalDto toFestivalDto(Long locationId) {
        Optional<Festival> byLocationId = typeRepository.findFestivalByLocationId(locationId);

        if (byLocationId.isPresent()) {
            Festival festival = byLocationId.get();

            return festivalDto.builder()
                    .endDate(festival.getEndDate())
                    .homepage(festival.getHomepage())
                    .place(festival.getPlace())
                    .startDate(festival.getStartDate())
                    .placeInfo(festival.getPlaceInfo())
                    .playTime(festival.getPlayTime())
                    .program(festival.getProgram())
                    .fee(festival.getFee())
                    .build();
        }

        return festivalDto.builder().build();
    }

    @Override
    public lodgeDto toLodgeDto(Long locationId) {
        Optional<Lodge> byLocationId = typeRepository.findLodgeByLocationId(locationId);

        if (byLocationId.isPresent()) {
            Lodge lodge = byLocationId.get();

            return lodgeDto.builder()
                    .checkInTime(lodge.getCheckInTime())
                    .checkOutTime(lodge.getCheckOutTime())
                    .parking(lodge.isParking())
                    .cooking(lodge.isCooking())
                    .build();
        }

        return lodgeDto.builder().build();
    }

    @Override
    public leportsDto toLeportsDto(Long locationId) {
        Optional<Leports> byLocationId = typeRepository.findLeportsByLocationId(locationId);

        if (byLocationId.isPresent()) {
            Leports leports = byLocationId.get();
            return leportsDto.builder()
                    .openPeriod(leports.getOpenPeriod())
                    .parking(leports.isParking())
                    .reservation(leports.getReservation())
                    .restDate(leports.getRestDate())
                    .fee(leports.getFee())
                    .useTime(leports.getUseTime())
                    .build();
        }
        return leportsDto.builder().build();
    }
}

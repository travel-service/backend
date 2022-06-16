package com.trablock.web.repository.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.entity.location.Coords;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.location.type.Restaurant;
import com.trablock.web.repository.location.type.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class TypeLocationRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    TypeLocationRepository typeLocationRepository;

    @Autowired
    AttractionRepository attractionRepository;
    @Autowired
    CultureRepository cultureRepository;
    @Autowired
    FestivalRepository festivalRepository;
    @Autowired
    LeportsRepository leportsRepository;
    @Autowired
    LodgeRepository lodgeRepository;
    @Autowired
    RestaurantRepository restaurantRepository;


    @Test
    void create() throws Exception {
        //given
        Location loc = Location.builder()
                .name("test")
                .address1("경기도 수원시 팔달구")
                .address2("권광로180번길 53-26")
                .coords(Coords.builder()
                        .latitude(37.123).longitude(127.123).build())
                .type(LocationType.RESTAURANT)
                .build();

        locationRepository.save(loc);
        Long locId = loc.getId();

        Restaurant restaurant = Restaurant.builder()
                .locationId(loc.getId())
                .popularMenu("아메리카노")
                .openTime("09:00")
                .packing(true)
                .parking(false)
                .restDate("연중무휴")
                .menu("음료")
                .build();

        //when
        Restaurant savedRest = restaurantRepository.save(restaurant);
        Restaurant findRest = typeLocationRepository.findRestaurantByLocationId(locId).get();

        //then
        assertThat(findRest).isEqualTo(savedRest);
        assertThat(findRest.getLocationId()).isEqualTo(savedRest.getLocationId());

        System.out.println("findRest = " + findRest);
        System.out.println("findRest.getLocationId() = " + findRest.getLocationId());

    }

    @Test
    void delete() throws Exception {
        //given
        Location loc = Location.builder()
                .name("test")
                .address1("경기도 수원시 팔달구")
                .address2("권광로180번길 53-26")
                .coords(Coords.builder()
                        .latitude(37.123).longitude(127.123).build())
                .type(LocationType.RESTAURANT)
                .build();

        locationRepository.save(loc);
        Long locId = loc.getId();


        Restaurant restaurant = Restaurant.builder()
                .locationId(loc.getId())
                .popularMenu("아메리카노")
                .openTime("09:00")
                .packing(true)
                .parking(false)
                .restDate("연중무휴")
                .menu("음료")
                .build();

        Long savedId = typeLocationRepository.saveRestaurant(restaurant);
        Optional<Restaurant> findRest = typeLocationRepository.findRestaurantByLocationId(locId);
        assertThat(findRest).isPresent();

        //when
        findRest.ifPresent(selectRest -> {
            typeLocationRepository.deleteRestaurant(findRest.get());
        });

        //then
        assertThat(typeLocationRepository.findRestaurantByLocationId(locId)).isEmpty();
        System.out.println("findRest = " + findRest);

    }


}
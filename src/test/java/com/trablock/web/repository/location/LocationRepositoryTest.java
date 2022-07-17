package com.trablock.web.repository.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.entity.location.Coords;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.location.type.Restaurant;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LocationRepositoryTest {

    @Autowired
    LocationRepository locationRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("id 리스트로 조회")
    void findByIdList() throws Exception {
        Location loc = Location.builder()
                .name("test1")
                .areaCode(123)
                .address1("경기도 수원시 팔달구")
                .address2("권광로180번길 53-26")
                .coords(Coords.builder()
                        .latitude(37.123).longitude(127.123).build()
                ).build();
        Location loc1 = Location.builder()
                .name("test2")
                .areaCode(123)
                .address1("경기도 수원시 팔달구")
                .address2("권광로180번길 53-26")
                .coords(Coords.builder()
                        .latitude(37.123).longitude(127.123).build()
                ).build();
        Location loc2 = Location.builder()
                .name("test3")
                .areaCode(123)
                .address1("경기도 수원시 팔달구")
                .address2("권광로180번길 53-26")
                .coords(Coords.builder()
                        .latitude(37.123).longitude(127.123).build()
                ).build();
        Location loc3 = Location.builder()
                .name("test4")
                .areaCode(123)
                .address1("경기도 수원시 팔달구")
                .address2("권광로180번길 53-26")
                .coords(Coords.builder()
                        .latitude(37.123).longitude(127.123).build()
                ).build();

        List<Location> locations = new ArrayList<>();
        for (Location location : Arrays.asList(loc, loc1, loc2, loc3)) {
            locations.add(location);
        }

        List<Location> savedList = locationRepository.saveAll(locations);

        List<Long> idList = savedList.stream().map(Location::getId).collect(Collectors.toList());

        List<Location> allLocationByIdIn = locationRepository.findAllLocationByIdIn(idList);
        assertThat(allLocationByIdIn.size()).isEqualTo(4);
        for (Location location : allLocationByIdIn) {
            System.out.println("location = " + location);
        }

    }

    @Test
    void create() {
        //given
        Location loc = Location.builder()
                .name("test")
                .areaCode(123)
                .address1("경기도 수원시 팔달구")
                .address2("권광로180번길 53-26")
                .coords(Coords.builder()
                        .latitude(37.123).longitude(127.123).build())
                .build();


        //when
        Location savedL = locationRepository.save(loc);
        Optional<Location> findL = locationRepository.findById(savedL.getId());

        //then
        assertThat(findL.get().getId()).isEqualTo(savedL.getId());
        assertThat(findL.get().getAddress1()).isEqualTo(savedL.getAddress1());
        assertThat(findL.get().getAddress2()).isEqualTo(savedL.getAddress2());
        assertThat(findL.get().getCoords()).isEqualTo(savedL.getCoords());

    }

    @Test
    void createWith_typeLocation() throws Exception {
        //given
        Location loc = Location.builder()
                .name("test")
                .address1("경기도 수원시 팔달구")
                .address2("권광로180번길 53-26")
                .coords(Coords.builder()
                        .latitude(37.123).longitude(127.123).build())
                .type(LocationType.RESTAURANT)
                .build();

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
        Location savedLoc = locationRepository.save(loc);
        Optional<Location> findLoc = locationRepository.findByNameContaining("tes");

        //then
        assertThat(findLoc.get()).isEqualTo(savedLoc);
        assertThat(findLoc.get().getType()).isEqualTo(savedLoc.getType());
        assertThat(findLoc.get().getType()).isEqualTo(savedLoc.getType());
    }

    @Test
    void read() {
        //given
        Location loc = Location.builder()
                .name("test")
                .address1("경기도 수원시 팔달구")
                .address2("권광로180번길 53-26")
                .coords(Coords.builder()
                        .latitude(37.123).longitude(127.123).build())
                .build();


        //when
        Location savedL = locationRepository.save(loc);
        Optional<Location> findL = locationRepository.findById(savedL.getId());

        //then
        assertThat(findL.get().getId()).isEqualTo(savedL.getId());
        assertThat(findL.get().getAddress1()).isEqualTo(savedL.getAddress1());
        assertThat(findL.get().getAddress2()).isEqualTo(savedL.getAddress2());
        assertThat(findL.get().getCoords()).isEqualTo(savedL.getCoords());
    }

    @Test
    void readByType() throws Exception {
        //given
        Location loc1 = Location.builder()
                .name("test")
                .address1("경기도 수원시 팔달구")
                .address2("권광로180번길 53-26")
                .coords(Coords.builder()
                        .latitude(37.123).longitude(127.123).build())
                .type(LocationType.RESTAURANT)
                .build();
        Location loc2 = Location.builder()
                .name("test2")
                .address1("경기도 수원시 권선구")
                .address2("권선로 694번길 25")
                .coords(Coords.builder()
                        .latitude(37.123).longitude(127.123).build())
                .type(LocationType.LODGE)
                .build();
        Location loc3 = Location.builder()
                .name("할리스")
                .address1("경기도 수원시 권선구")
                .address2("권광로180번길 53-26")
                .coords(Coords.builder()
                        .latitude(37.123).longitude(127.123).build())
                .type(LocationType.RESTAURANT)
                .build();

        Location save1 = locationRepository.save(loc1);
        Location save2 = locationRepository.save(loc2);
        Location save3 = locationRepository.save(loc3);

        //when
        List<Location> locationByType = locationRepository.findLocationByType(LocationType.RESTAURANT);

        //then
        assertThat(locationByType.size()).isEqualTo(2);

        for (Location location : locationByType) {
            System.out.println("location = " + location.getName());
        }

    }


    @Test
    void update() throws Exception {

        //given
        Location loc = Location.builder()
                .name("test")
                .address1("경기도 수원시 팔달구")
                .address2("권광로180번길 53-26")
                .coords(Coords.builder()
                        .latitude(37.123).longitude(127.123).build())
                .build();

        Location savedL = locationRepository.save(loc);

        assertThat(savedL.getName()).isEqualTo("test");

        //when
        loc.changeName("랑데자뷰");
        Location findL = locationRepository.findById(savedL.getId()).get();

        //then
        assertThat(findL.getName()).isEqualTo("랑데자뷰");

    }

    @Test
    void delete() throws Exception {
        //given
        Location loc1 = Location.builder()
                .name("test1")
                .address1("경기도 수원시 팔달구")
                .address2("권광로180번길 53-26")
                .coords(Coords.builder()
                        .latitude(37.123).longitude(127.123).build())
                .build();

        Location loc2 = Location.builder()
                .name("test2")
                .address1("경기도 수원시 팔달구")
                .address2("권광로180번길 53-26")
                .coords(Coords.builder()
                        .latitude(37.123).longitude(127.123).build())
                .build();

        Location save1 = locationRepository.save(loc1);
        Location save2 = locationRepository.save(loc2);

        Optional<Location> find1 = locationRepository.findByName("test1");

        //when
        find1.ifPresent(findLoc -> {
            locationRepository.deleteById(find1.get().getId());
        });

        List<Location> all = locationRepository.findAll();

        //then
        assertThat(all.size()).isEqualTo(1);

    }

    @Test
    void 조회() {
        List<Location> all = locationRepository.findAll();
        System.out.println("all = " + all);
        for (Location location : all) {
            System.out.println("location = " + location);
        }
    }

}

package com.trablock.demo.repository.location;

import com.trablock.demo.domain.location.RestaurantLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantLocationRepository extends JpaRepository<RestaurantLocation, Long> {

//    @Query
//    List<RestaurantLocation> findByAreaCode(int areaCode);
//
//    @Query
//    List<RestaurantLocation> findByParkingTrue();

}

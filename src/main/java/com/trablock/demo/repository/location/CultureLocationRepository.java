package com.trablock.demo.repository.location;

import com.trablock.demo.domain.location.CultureLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CultureLocationRepository extends JpaRepository<CultureLocation, Long> {

//    @Query
//    List<CultureLocation> findByAreaCode(int areaCode);
//
//    @Query
//    List<CultureLocation> findByParkingTrue();

}

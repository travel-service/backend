package com.trablock.demo.repository.location;

import com.trablock.demo.domain.location.AttractionLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttractionLocationRepository extends JpaRepository<AttractionLocation, Long> {

//    @Query
//    List<AttractionLocation> findByAreaCode(int areaCode);
//
//    @Query
//    List<AttractionLocation> findByParkingTrue();

}

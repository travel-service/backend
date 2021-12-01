package com.trablock.demo.repository.location;

import com.trablock.demo.domain.location.LodgeLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LodgeLocationRepository extends JpaRepository<LodgeLocation, Long> {

//    @Query
//    List<LodgeLocation> findByAreaCode(int areaCode);
//
//    @Query
//    List<LodgeLocation> findByParkingTrue();
}

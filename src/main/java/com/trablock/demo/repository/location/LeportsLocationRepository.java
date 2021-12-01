package com.trablock.demo.repository.location;

import com.trablock.demo.domain.location.LeportsLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeportsLocationRepository extends JpaRepository<LeportsLocation, Long> {

//    @Query
//    List<LeportsLocation> findByAreaCode(int areaCode);
//
//    @Query
//    List<LeportsLocation> findByParkingTrue();
}

package com.trablock.demo.repository.location;

import com.trablock.demo.domain.location.SystemLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SystemLocationRepository extends JpaRepository<SystemLocation, Long> {

    List<SystemLocation> findAll();

//    @Query
//    List<SystemLocation> findByAreaCode(int areaCode);

}

package com.trablock.demo.repository.location;

import com.trablock.demo.domain.location.FestivalLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FestivalLocationRepository extends JpaRepository<FestivalLocation, Long> {

//    @Query
//    List<FestivalLocation> findByAreaCode(int areaCode);

}
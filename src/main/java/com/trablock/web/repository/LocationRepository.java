package com.trablock.web.repository;

import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("select m from Location as m where m.id= :id")
    Location findLocationId(Long id);

    @Query("select l from Location as l")
    List<Location> findAllById();
}

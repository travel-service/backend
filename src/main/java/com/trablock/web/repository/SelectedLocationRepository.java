package com.trablock.web.repository;

import com.trablock.web.entity.plan.SelectedLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SelectedLocationRepository extends JpaRepository<SelectedLocation, Long> {

    @Query("select s.id from SelectedLocation s")
    List<SelectedLocation> findSelectedLocationList();
}

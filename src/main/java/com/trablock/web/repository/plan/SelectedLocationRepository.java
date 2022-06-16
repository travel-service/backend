package com.trablock.web.repository.plan;

import com.trablock.web.entity.plan.Concept;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.SelectedLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SelectedLocationRepository extends JpaRepository<SelectedLocation, Long> {

    @Query("select s.id from SelectedLocation s")
    List<SelectedLocation> findSelectedLocationList();

    @Query("select s from SelectedLocation s where s.plan = :id")
    List<SelectedLocation> findSelectedLocationByPlanId(@Param("id") Plan id);
}

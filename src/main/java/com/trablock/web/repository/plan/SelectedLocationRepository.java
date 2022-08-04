package com.trablock.web.repository.plan;

import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.SelectedLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SelectedLocationRepository extends JpaRepository<SelectedLocation, Long> {

    // TODO TEST
    @Query("select s from SelectedLocation s where s.plan = :plan")
    List<SelectedLocation> findSelectedLocationByPlanId(@Param("plan") Plan plan);

    // TODO TEST
    @Query("select s.location.id from SelectedLocation s where s.plan = :plan")
    List<Long> findLocationIdByPlanId(@Param("plan") Plan plan);
}

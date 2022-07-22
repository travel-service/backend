package com.trablock.web.repository.plan;

import com.trablock.web.entity.plan.Day;
import com.trablock.web.entity.plan.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DayRepository extends JpaRepository<Day, Long> {

    // TODO TEST
    @Query("select d from Day d where d.plan = :plan")
    List<Day> findDaysByPlan(@Param("plan") Plan plan);

}

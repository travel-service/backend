package com.trablock.web.repository.plan;

import com.trablock.web.entity.plan.Day;
import com.trablock.web.entity.plan.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DayRepository extends JpaRepository<Day, Long> {

//    @Query("select d from Day d where d.id = :id AND d.days = :days")
//    Day findByIdToList(@Param("id") Long id, @Param("days") int days);
//
//    @Query("select d.id from Day d where d.plan = :id")
//    List<Long> findByDayIdToList(@Param("id") Plan id);

    @Query("select d from Day d where d.plan = :planId")
    List<Day> findByDayToList(@Param("planId") Plan id);

//    @Query("select d.days from Day d where d.plan = :id")
//    List<Integer> findByIdForDaysToList(@Param("id") Plan id);
}

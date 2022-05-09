package com.trablock.web.repository;

import com.trablock.web.entity.plan.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    Plan findPlanById(Long id);

}

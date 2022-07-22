package com.trablock.web.repository.plan;

import com.trablock.web.entity.plan.Concept;
import com.trablock.web.entity.plan.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConceptRepository extends JpaRepository<Concept, Long> {

    // TODO TEST
    @Query("select c.conceptName from Concept c where c.plan.id = :planId")
    List<String> findConceptNameByPlanId(@Param("planId") Long planId);

    // TODO TEST
    @Query("select c from Concept c where c.plan = :plan")
    List<Concept> findConceptByPlan(@Param("plan") Plan plan);

}

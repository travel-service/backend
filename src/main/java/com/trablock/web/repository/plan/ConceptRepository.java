package com.trablock.web.repository.plan;

import com.trablock.web.entity.plan.Concept;
import com.trablock.web.entity.plan.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConceptRepository extends JpaRepository<Concept, Long> {

    @Query("select c.conceptName from Concept c where c.plan = :planId")
    List<String> findByIdToList(@Param("planId") Plan id);

    @Query("select c.plan from Concept c where c.plan = :planId")
    Plan findId(@Param("planId")Plan id);

    @Query("select c from Concept c where c.plan = :planId")
    List<Concept> findConceptByPlanId(@Param("planId") Plan id);


}

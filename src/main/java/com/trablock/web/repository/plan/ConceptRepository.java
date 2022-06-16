package com.trablock.web.repository.plan;

import com.trablock.web.entity.plan.Concept;
import com.trablock.web.entity.plan.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConceptRepository extends JpaRepository<Concept, Long> {

    @Query("select c.conceptName from Concept c where c.plan = :id")
    List<String> findByIdToList(@Param("id") Plan id);

    @Query("select c.plan from Concept c where c.plan = :id")
    Plan findId(@Param("id")Plan id);

    @Query("select c from Concept c where c.plan = :id")
    List<Concept> findConceptByPlanId(@Param("id") Plan id);


}

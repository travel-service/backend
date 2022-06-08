package com.trablock.web.repository.plan;

import com.trablock.web.dto.plan.ConceptDto;
import com.trablock.web.entity.plan.Concept;
import com.trablock.web.entity.plan.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConceptRepository extends JpaRepository<Concept, Long> {

    @Query("select c from Concept as c where c.id= :id")
    Concept findConceptById(Long conceptId);

    @Query("select c.conceptName from Concept c where c.plan = :id")
    List<String> findByIdToList(@Param("id") Plan id);

    @Query("select c.plan from Concept c where c.plan = :id")
    Plan findId(@Param("id")Plan id);
}

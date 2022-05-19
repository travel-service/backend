package com.trablock.web.repository.plan;

import com.trablock.web.entity.plan.Concept;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConceptRepository extends JpaRepository<Concept, Long> {

    //@Query("select c from Concept as c where c.id= :id")
    //Concept findConceptById(Long conceptId);
}

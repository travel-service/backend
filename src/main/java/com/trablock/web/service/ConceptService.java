package com.trablock.web.service;

import com.trablock.web.entity.plan.Concept;
import com.trablock.web.repository.ConceptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConceptService {

    @PersistenceContext
    EntityManager em;
    private final ConceptRepository conceptRepository;

    @Transactional
    public void saveConcept(Concept concept) {
        conceptRepository.save(concept);
    }

//    public Concept findOne(Long conceptId) {
//        return conceptRepository.findConceptById(conceptId);
//    }
}

package com.trablock.web.service.plan;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.Concept;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.repository.ConceptRepository;
import com.trablock.web.service.plan.planInterface.ConceptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConceptServiceImpl implements ConceptService {

    @PersistenceContext
    EntityManager em;
    private final ConceptRepository conceptRepository;

    @Transactional
    public void saveConcept(Concept concept) {
        conceptRepository.save(concept);
    }

    @Transactional
    @Override
    public void createConcept(Form form, Member findMemberId, Plan plan) {
        for (int i = 0; i < form.getConceptForm().getConcept().size(); i++) {
            Concept concept = Concept.builder()
                    .plan(plan)
                    .member(findMemberId)
                    .conceptName(form.getConceptForm().getConcept().get(i))
                    .build();

            saveConcept(concept);
        }
    }

}

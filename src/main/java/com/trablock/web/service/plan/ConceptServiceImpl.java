package com.trablock.web.service.plan;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.plan.Concept;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.repository.plan.ConceptRepository;
import com.trablock.web.service.plan.planInterface.ConceptService;
import com.trablock.web.service.plan.planInterface.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConceptServiceImpl implements ConceptService {

    private final ConceptRepository conceptRepository;
    private final PlanService planService;

    @Transactional
    public void saveConcept(Concept concept) {
        conceptRepository.save(concept);
    }

    @Transactional
    @Override
    public void createConcept(Form form, HttpServletRequest request, Plan plan) {
        for (int i = 0; i < form.getConceptForm().getConcept().size(); i++) {
            Concept concept = Concept.builder()
                    .plan(plan)
//                    .member(planService.findMemberId(request))
                    .conceptName(form.getConceptForm().getConcept().get(i))
                    .build();

            saveConcept(concept);
        }
    }
}

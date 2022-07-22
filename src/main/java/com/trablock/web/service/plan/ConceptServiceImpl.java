package com.trablock.web.service.plan;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.plan.Concept;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.repository.plan.ConceptRepository;
import com.trablock.web.repository.plan.PlanRepository;
import com.trablock.web.service.plan.interfaceC.ConceptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConceptServiceImpl implements ConceptService {

    private final ConceptRepository conceptRepository;
    private final PlanRepository planRepository;

    @Override
    @Transactional
    // TODO TEST
    public void saveConcept(Concept concept) {
        conceptRepository.save(concept);
    }

    @Override
    @Transactional
    // TODO TEST
    public void createConcept(Form form, HttpServletRequest request, Long planId) {
        Plan plan = planRepository.findPlanById(planId).orElseThrow();

        ArrayList<Concept> conceptList = new ArrayList<>();

        for (int i = 0; i < form.getConceptForm().getConcept().size(); i++) {
            Concept concept = Concept.builder()
                    .plan(plan)
                    .conceptName(form.getConceptForm().getConcept().get(i))
                    .build();

            conceptList.add(concept);
        }

        conceptRepository.saveAll(conceptList);
    }

    @Override
    // TODO TEST
    public List<String> findConceptIdForPlanIdToList(Long planId) {
        return conceptRepository.findConceptNameByPlanId(planId);
    }

    /**
     * Concept Update
     *
     * @param planId
     * @param request
     * @param form
     */
    @Override
    @Transactional
    // TODO TEST
    public void updateConcept(Long planId, HttpServletRequest request, Form form) {
        Plan plan = planRepository.findPlanById(planId).orElseThrow();
        if (plan.getConcepts() == null || !plan.getConcepts().isEmpty())
            removeConcept(plan);
        createConcept(form, request, plan.getId());
    }

    /**
     * Concept delete
     *
     * @param plan
     */
    @Override
    @Transactional
    // TODO TEST
    public void removeConcept(Plan plan) {
        List<Concept> conceptList = conceptRepository.findConceptByPlan(plan);

        if (conceptList == null || conceptList.isEmpty()) {
            return;
        }
        conceptList.forEach(conceptRepository::delete);
    }
}

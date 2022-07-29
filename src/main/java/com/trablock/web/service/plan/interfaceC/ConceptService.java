package com.trablock.web.service.plan.interfaceC;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.plan.Concept;
import com.trablock.web.entity.plan.Plan;

import java.util.List;

public interface ConceptService {

    List<Concept> createConcept(Form form, Long plan);

    List<String> findConceptIdForPlanIdToList(Long planId);

    List<Concept> updateConcept(Long planId, Form form);

    void removeConcept(Plan plan);
}

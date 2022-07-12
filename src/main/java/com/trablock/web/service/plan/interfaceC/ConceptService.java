package com.trablock.web.service.plan.interfaceC;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.plan.Concept;
import com.trablock.web.entity.plan.Plan;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ConceptService {

    void saveConcept(Concept concept);

    void createConcept(Form form, HttpServletRequest request, Long plan);

    List<String> findConceptIdForPlanIdToList(Long planId);

    Plan findPlanId(Long planId);

    void updateConcept(Long id, HttpServletRequest request, Form form);

    void removeConcept(Plan plan);
}

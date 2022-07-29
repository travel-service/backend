package com.trablock.web.service.plan.interfaceC;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.Concept;
import com.trablock.web.entity.plan.Plan;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ConceptService {

    void createConcept(Form form, Long plan);

    List<String> findConceptIdForPlanIdToList(Long planId);

    void updateConcept(Long planId, Form form);

    void removeConcept(Plan plan);
}

package com.trablock.web.service.plan.planInterface;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.Concept;
import com.trablock.web.entity.plan.Plan;

public interface ConceptService {

    void saveConcept(Concept concept);

    void createConcept(Form form, Member findMemberId, Plan plan);

}

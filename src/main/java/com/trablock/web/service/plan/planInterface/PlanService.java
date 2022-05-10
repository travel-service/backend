package com.trablock.web.service.plan.planInterface;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.Plan;

import java.util.List;

public interface PlanService {

    void savePlan(Plan plan);

    Plan findOne(Long planId);

    List<Plan> findAll();

    Plan createPlan(Form form, Member findMemberId);

}

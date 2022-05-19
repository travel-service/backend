package com.trablock.web.service.plan.planInterface;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.Plan;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PlanService {

    void savePlan(Plan plan);

    Plan findOne(Long planId);

    Plan createPlan(Form form, HttpServletRequest request);

    Member findMemberId(HttpServletRequest request);

    List<Plan> findMainPlanDirectoryMain(HttpServletRequest request);

    public List<Plan> findTrashPlanDirectoryMain(HttpServletRequest request);

    public void cancelPlan(Long planId);

    public void deletePlan(Long planId);

    void revertPlan(Long planId);

}

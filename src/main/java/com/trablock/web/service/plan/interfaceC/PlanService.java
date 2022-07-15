package com.trablock.web.service.plan.interfaceC;

import com.trablock.web.controller.form.Form;
import com.trablock.web.dto.plan.PlanDto;
import com.trablock.web.dto.plan.UserPlanUpdateDto;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.Plan;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface PlanService {

    void savePlan(Plan plan);

    PlanDto getOnePlanDto(Long planId, Member member);

    Member findMemberId(HttpServletRequest request);

    Plan createPlan(Form form, HttpServletRequest request);

    List<Plan> findMainPlanDirectoryMain(HttpServletRequest request);

    List<Plan> findTrashPlanDirectoryMain(HttpServletRequest request);

    void cancelPlan(Long planId, HttpServletRequest request);

    void deletePlan(Long planId, HttpServletRequest request);

    void revertPlan(Long planId, HttpServletRequest request);

    void finishedPlan(Long planId);

    void updateUserPlanContent(Long planId, HttpServletRequest request, UserPlanUpdateDto userPlanUpdateDto);

    int countPlan(HttpServletRequest request);

    int countTrashPlan(HttpServletRequest request);

    Plan returnPlan(Long planId, HttpServletRequest request);
}

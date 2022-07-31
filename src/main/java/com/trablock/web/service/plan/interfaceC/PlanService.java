package com.trablock.web.service.plan.interfaceC;

import com.trablock.web.controller.form.Form;
import com.trablock.web.controller.form.StateChangeForm;
import com.trablock.web.converter.Converter;
import com.trablock.web.converter.Converter.MainDirectory;
import com.trablock.web.dto.plan.PlanDto;
import com.trablock.web.dto.plan.PlanInfoDto;
import com.trablock.web.dto.plan.UserPlanUpdateDto;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.Plan;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PlanService {

    void savePlan(Plan plan);

    PlanDto getOnePlanDto(Long planId, Member member);

    Member getMemberFromPayload(HttpServletRequest request);

    Plan createPlan(Form form, Member member);

    List<Plan> findMainPlanDirectoryMain(Member member);

    List<Plan> findTrashPlanDirectoryMain(HttpServletRequest request);

    void cancelPlan(StateChangeForm stateChangeForm, Member member);

    void deletePlan(StateChangeForm stateChangeForm, Member member);

    void revertPlan(StateChangeForm stateChangeForm, Member member);

    void finishedPlan(Long planId);

    void updateUserPlanContent(Long planId, Member member, UserPlanUpdateDto userPlanUpdateDto);

    int countPlan(Member member);

    int countTrashPlan(Member member);

    Plan returnPlan(Long planId, Member member);

    MainDirectory findPlanInfo(Member memberId, int planCount);

    void unFinishedPlan(Long planId);
}

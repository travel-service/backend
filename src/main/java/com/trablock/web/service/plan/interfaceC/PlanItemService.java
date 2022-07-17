package com.trablock.web.service.plan.interfaceC;

import com.trablock.web.controller.form.MoveDirectoryForm;
import com.trablock.web.controller.form.UserDirectoryForm;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.PlanItem;
import com.trablock.web.entity.plan.UserDirectory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PlanItemService {

    void savePlanItem(PlanItem planItem);

    void moveUserPlan(MoveDirectoryForm moveDirectoryForm, Long memberId);

    void deleteMapping(UserDirectoryForm userDirectoryForm);

    List<Plan> findUserPlanDirectoryUser(UserDirectory id);

    List<Integer> countPlan(List<UserDirectory> userDirectories);
}

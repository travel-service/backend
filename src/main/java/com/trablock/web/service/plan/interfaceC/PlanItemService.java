package com.trablock.web.service.plan.interfaceC;

import com.trablock.web.controller.form.MoveDirectoryForm;
import com.trablock.web.controller.form.UserDirectoryForm;
import com.trablock.web.converter.Converter;
import com.trablock.web.converter.Converter.PlanMoveToUserDirectory;
import com.trablock.web.dto.plan.PlansDeleteDto;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.UserDirectory;

import java.util.List;

import static com.trablock.web.converter.Converter.*;

public interface PlanItemService {

    PlanMoveToUserDirectory moveUserPlan(MoveDirectoryForm moveDirectoryForm, Long memberId);

    void deleteMapping(UserDirectoryForm userDirectoryForm);

    List<Plan> findUserPlanDirectoryUser(UserDirectory id);

    List<Integer> countPlan(List<UserDirectory> userDirectories);

    DeletePlans deletePlans(Long userDirectoryId, PlansDeleteDto plansDeleteDto);
}

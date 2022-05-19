package com.trablock.web.service.plan.planInterface;

import com.trablock.web.controller.form.MoveDirectoryForm;
import com.trablock.web.controller.form.StateChangeForm;
import com.trablock.web.controller.form.UserDirectoryForm;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.PlanItem;
import com.trablock.web.entity.plan.UserDirectory;

import java.util.List;

public interface PlanItemService {

    void savePlanItem(PlanItem planItem);

    void moveUserPlan(MoveDirectoryForm moveDirectoryForm);

    void deleteMapping(UserDirectoryForm userDirectoryForm);

    List<Plan> findUserPlanDirectoryUser(UserDirectory id);
}

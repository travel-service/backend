package com.trablock.web.service.plan;

import com.trablock.web.controller.form.MoveDirectoryForm;
import com.trablock.web.controller.form.UserDirectoryForm;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.PlanItem;
import com.trablock.web.entity.plan.Status;
import com.trablock.web.entity.plan.UserDirectory;
import com.trablock.web.repository.plan.PlanItemRepository;
import com.trablock.web.repository.plan.PlanRepository;
import com.trablock.web.repository.plan.UserDirectoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlanItemService {

    private final PlanItemRepository planItemRepository;
    private final PlanRepository planRepository;
    private final UserDirectoryRepository userDirectoryRepository;

    @Transactional
    public void savePlanItem(PlanItem planItem) {
        planItemRepository.save(planItem);
    }

    //유저가 만든 플랜을 main 디렉터리에서 -> user 디렉터리로 이동
    @Transactional
    public void moveUserPlan(MoveDirectoryForm moveDirectoryForm) {

        UserDirectory userDirectoryId = userDirectoryRepository.findUserDirectoryById(moveDirectoryForm.getUserDirectoryId());

        for (int i = 0; i < moveDirectoryForm.getPlanId().size(); i++) {

            Plan planId = planRepository.findPlanById(moveDirectoryForm.getPlanId().get(i));

            PlanItem planItem = PlanItem.builder()
                    .userDirectory(userDirectoryId)
                    .plan(planId)
                    .status(Status.UNDELETE)
                    .build();

            planItemRepository.save(planItem);
        }
    }

    @Transactional
    public void deleteMapping(UserDirectoryForm userDirectoryForm) {
        for (int i = 0; i < userDirectoryForm.getUserDirectoryId().size(); i++) {
            List<PlanItem> planItemByUserDirectoryId = planItemRepository.findPlanItemByUserDirectoryId(userDirectoryForm.getUserDirectoryId().get(i));
            for (PlanItem planItem : planItemByUserDirectoryId) {
                planItem.delete();
            }
        }
    }

    public List<Plan> findUserPlanDirectoryUser(UserDirectory id) {
        return planItemRepository.findPlanItemByPI(id);
    }
}

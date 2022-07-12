package com.trablock.web.service.plan;

import com.trablock.web.controller.form.MoveDirectoryForm;
import com.trablock.web.controller.form.UserDirectoryForm;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.PlanItem;
import com.trablock.web.entity.plan.enumtype.Status;
import com.trablock.web.entity.plan.UserDirectory;
import com.trablock.web.repository.plan.PlanItemRepository;
import com.trablock.web.repository.plan.PlanRepository;
import com.trablock.web.repository.plan.UserDirectoryRepository;
import com.trablock.web.service.plan.interfaceC.PlanItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlanItemServiceImpl implements PlanItemService {

    private final PlanItemRepository planItemRepository;
    private final PlanRepository planRepository;
    private final UserDirectoryRepository userDirectoryRepository;

    @Override
    @Transactional
    public void savePlanItem(PlanItem planItem) {
        planItemRepository.save(planItem);
    }

    //유저가 만든 플랜을 main 디렉터리에서 -> user 디렉터리로 이동
    @Override
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

    @Override
    @Transactional
    public void deleteMapping(UserDirectoryForm userDirectoryForm) {
        for (int i = 0; i < userDirectoryForm.getUserDirectoryId().size(); i++) {
            List<PlanItem> planItemByUserDirectoryId = planItemRepository.findPlanItemByUserDirectoryId(userDirectoryForm.getUserDirectoryId().get(i));
            for (PlanItem planItem : planItemByUserDirectoryId) {
                planItem.delete();
            }
        }
    }

    @Override
    public List<Plan> findUserPlanDirectoryUser(UserDirectory id) {
        return planItemRepository.findPlanItemByPI(id);
    }

    /**
     * user directory에 담겨 있는 플랜 갯수 반환
     * @param userDirectories
     * @return
     */
    @Override
    public List<Integer> countPlan(List<UserDirectory> userDirectories) {
        List<Integer> countPlanList = new ArrayList<>();

        for (UserDirectory userDirectory : userDirectories) {
            Integer integer = planItemRepository.countPlan(userDirectory);

            countPlanList.add(integer);
        }

        return countPlanList;
    }
}

package com.trablock.web.service.plan;

import com.trablock.web.controller.form.MoveDirectoryForm;
import com.trablock.web.controller.form.UserDirectoryForm;
import com.trablock.web.dto.plan.PlansDeleteDto;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.PlanItem;
import com.trablock.web.entity.plan.UserDirectory;
import com.trablock.web.entity.plan.enumtype.PlanItemStatus;
import com.trablock.web.entity.plan.enumtype.PlanStatus;
import com.trablock.web.global.HTTPStatus;
import com.trablock.web.repository.plan.PlanItemRepository;
import com.trablock.web.repository.plan.PlanRepository;
import com.trablock.web.repository.plan.UserDirectoryRepository;
import com.trablock.web.service.plan.interfaceC.PlanItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.trablock.web.converter.Converter.*;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlanItemServiceImpl implements PlanItemService {

    private final PlanItemRepository planItemRepository;
    private final PlanRepository planRepository;
    private final UserDirectoryRepository userDirectoryRepository;

    // 유저가 만든 플랜을 main 디렉터리에서 -> user 디렉터리로 이동
    @Override
    @Transactional
    public PlanMoveToUserDirectory moveUserPlan(MoveDirectoryForm moveDirectoryForm, Long memberId) {

        UserDirectory userDirectoryId = userDirectoryRepository.findUserDirectoryById(moveDirectoryForm.getUserDirectoryId(), memberId);

        ArrayList<PlanItem> planItemList = new ArrayList<>();

        for (int i = 0; i < moveDirectoryForm.getPlanId().size(); i++) {
            Plan plan = planRepository.findPlanById(moveDirectoryForm.getPlanId().get(i)).orElseThrow();

            Long countPlanItem = planItemRepository.countPlanItem(userDirectoryId.getId(), plan.getId());

            if (countPlanItem != 0) {
                PlanItem planItem = planItemRepository.getUserDirectoriesIdByPlanIdAndUdir(userDirectoryId.getId(), plan.getId());

                planItem.revertPlan();

            } else {
                PlanItem planItem = PlanItem.builder()
                        .userDirectory(userDirectoryId)
                        .plan(plan)
                        .planItemStatus(PlanItemStatus.UNDELETE)
                        .build();

                planItemList.add(planItem);
            }
        }

        planItemRepository.saveAll(planItemList);

        String message = "플랜이 정상적으로 디렉터리로 이동되었습니다.";

        return new PlanMoveToUserDirectory(HTTPStatus.Created.getCode(), message);
    }

    @Override
    @Transactional
    public void deleteMapping(UserDirectoryForm userDirectoryForm) {
        for (int i = 0; i < userDirectoryForm.getUserDirectoryId().size(); i++) {
            List<PlanItem> planItemByUserDirectoryId = planItemRepository.findPlanItemByUserDirectoryId(userDirectoryForm.getUserDirectoryId().get(i));
            for (PlanItem planItem : planItemByUserDirectoryId) {
                planRepository.deleteById(planItem.getId());
            }
        }
    }

    @Override
    public List<Plan> findUserPlanDirectoryUser(UserDirectory id) {
        return planItemRepository.findPlanItemByPI(id, PlanItemStatus.UNDELETE, PlanStatus.MAIN);
    }

    /**
     * user directory에 담겨 있는 플랜 갯수 반환
     *
     * @param userDirectories
     * @return
     */
    @Override
    public List<Integer> countPlan(List<UserDirectory> userDirectories) {
        List<Integer> countPlanList = new ArrayList<>();

        for (UserDirectory userDirectory : userDirectories) {
            List<PlanItem> planItemList = planItemRepository.countPlan(userDirectory, PlanItemStatus.UNDELETE, PlanStatus.MAIN);

            countPlanList.add(planItemList.size());
        }

        return countPlanList;
    }

    @Override
    @Transactional
    public DeletePlans deletePlans(Long userDirectoryId, PlansDeleteDto plansDeleteDto) {

        for (Long planId : plansDeleteDto.getPlanIds()) {
            PlanItem planItem = planItemRepository.getUserDirectoriesIdByPlanIdAndUdir(userDirectoryId, planId);

            planItem.deletePlan();
        }

        String message = "플랜이 삭제되었습니다.";
        return new DeletePlans(HTTPStatus.NoContent.getCode(), message);
    }
}

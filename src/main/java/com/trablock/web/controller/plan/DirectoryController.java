package com.trablock.web.controller.plan;


import com.trablock.web.controller.form.MoveDirectoryForm;
import com.trablock.web.controller.form.StateChangeForm;
import com.trablock.web.controller.form.UserDirectoryForm;
import com.trablock.web.dto.plan.DirectoryNameUpdateDto;
import com.trablock.web.dto.plan.PlanDirectoryDto;
import com.trablock.web.dto.plan.UserDirectoryDto;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.UserDirectory;
import com.trablock.web.service.plan.PlanItemService;
import com.trablock.web.service.plan.PlanService;
import com.trablock.web.service.plan.UserDirectoryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DirectoryController {

    private final PlanService planService;
    private final UserDirectoryService userDirectoryService;
    private final PlanItemService planItemService;


    //main directory get
    @GetMapping("/main-directory")
    public MainDirectory mainPlans(HttpServletRequest request) {
        List<Plan> planDirectoryMain = planService.findMainPlanDirectoryMain(request);
        List<PlanDirectoryDto> collect = getPlanDirectoryDtos(planDirectoryMain);

        int planCount = planService.countPlan(request); // 플랜 갯수 반환
        return new MainDirectory(planCount, collect);
    }

    @Data
    @AllArgsConstructor
    static class MainDirectory<T> {
        private int planCount;
        private T mainDirectory;
    }

    //main-user directory get
    @GetMapping("/main-user-directory")
    public MainUserDirectory usersPlans(HttpServletRequest request) {
        List<UserDirectory> mainUserDirectoryMain = userDirectoryService.findMainUserDirectoryMain(request);
        List<UserDirectoryDto> collect = mainUserDirectoryMain.stream()
                .map(o -> new UserDirectoryDto(o.getId(), o.getDirectoryName()))
                .collect(Collectors.toList());

        return new MainUserDirectory(collect);
    }


    @Data
    @AllArgsConstructor
    static class MainUserDirectory<T> {

        private T mainUserDirectory;
    }

    //trash directory get
    @GetMapping("/trash-directory")
    public TrashDirectory trashPlans(HttpServletRequest request) {
        List<Plan> planDirectoryMain = planService.findTrashPlanDirectoryMain(request);
        List<PlanDirectoryDto> collect = getPlanDirectoryDtos(planDirectoryMain);

        return new TrashDirectory(collect);
    }

    @Data
    @AllArgsConstructor
    static class TrashDirectory<T> {

        private T trashDirectory;
    }

    // user directory get
    @GetMapping("/user-directory/{userDirectoryId}")
    public ShowUserDirectory usersDirectoryPlans(@PathVariable("userDirectoryId") UserDirectory id) {
        List<Plan> userPlanDirectoryUser = planItemService.findUserPlanDirectoryUser(id);
        List<PlanDirectoryDto> collect = userPlanDirectoryUser.stream()
                .map(m -> new PlanDirectoryDto(m.getId(), m.getName(), m.getPeriods(), m.getCreatedDate(), m.getPlanComplete()))
                .collect(Collectors.toList());

        return new ShowUserDirectory(collect);
    }

    @Data
    @AllArgsConstructor
    static class ShowUserDirectory<T> {

        private T userDirectory;
    }

    //플랜 삭제(main -> trash)
    @PostMapping("/main-directory/cancel")
    public String cancelPlan(@RequestBody StateChangeForm stateChangeForm) {
        for (int i = 0; i < stateChangeForm.getPlanId().size(); i++) {
            planService.cancelPlan(stateChangeForm.getPlanId().get(i));
        }
        return "redirect:/main-directory";
    }

    //플랜 복구(trash -> main)
    @PostMapping("/trash-directory/revert")
    public String revertPlan(@RequestBody StateChangeForm stateChangeForm) {
        for (int i = 0; i < stateChangeForm.getPlanId().size(); i++) {
            planService.revertPlan(stateChangeForm.getPlanId().get(i));
        }
        return "redirect:/main-directory";
    }

    //플랜 영구 삭제(trash -> delete)
    @PostMapping("/trash-directory/delete")
    public String deletePlan(@RequestBody StateChangeForm stateChangeForm) {
        for (int i = 0; i < stateChangeForm.getPlanId().size(); i++) {
            planService.deletePlan(stateChangeForm.getPlanId().get(i));
        }
        return "redirect:/trash-directory";
    }

    //user directory 생성
    @PostMapping("/create/user-directory")
    public String createUserDirectory(HttpServletRequest request, @RequestBody UserDirectoryForm userDirectoryForm, HttpServletResponse response) {
        userDirectoryService.createUserDirectory(request, userDirectoryForm, response);
        return "redirect:/main-directory";
    }

    //user directory 삭제(undelete -> delete)
    @PostMapping("/delete/user-directory")
    public String deleteUserDirectory(@RequestBody UserDirectoryForm userDirectoryForm) {
        for (int i = 0; i < userDirectoryForm.getUserDirectoryId().size(); i++) {
            userDirectoryService.deleteUserDirectory(userDirectoryForm.getUserDirectoryId().get(i));
        }
        planItemService.deleteMapping(userDirectoryForm);

        return "redirect:/main-directory";
    }

    //plan 이동(main 디렉터리 -> user 디렉터리)
    @PostMapping("/move/user-directory")
    public String moveUserDirectory(@RequestBody MoveDirectoryForm moveDirectoryForm) {
        planItemService.moveUserPlan(moveDirectoryForm);
        return "redirect:/main-directory";
    }

//    // 완료된 플랜 불러오기
//    @PostMapping("/show/finished/{planId}")
//    public String showFinishedPlan(@PathVariable("planId") Long id) {
//        return planService.isFinishedPlan(id);
//    }

    // user directory 이름 변경
    @PostMapping("/update/user-directory-name/{userDirectoryId}")
    public void updateUserDirectoryName(@PathVariable("userDirectoryId") Long id, @RequestBody DirectoryNameUpdateDto directoryNameUpdateDto) {
        userDirectoryService.updateDirectoryName(id, directoryNameUpdateDto);
    }

    private List<PlanDirectoryDto> getPlanDirectoryDtos(List<Plan> planDirectoryMain) {
        return planDirectoryMain.stream()
                .map(m -> new PlanDirectoryDto(m.getId(), m.getName(), m.getPeriods(), m.getCreatedDate(), m.getPlanComplete()))
                .collect(Collectors.toList());
    }
}

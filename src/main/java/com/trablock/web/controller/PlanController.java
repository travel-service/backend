package com.trablock.web.controller;

import com.trablock.web.controller.form.MoveDirectoryForm;
import com.trablock.web.controller.form.UserDirectoryForm;
import com.trablock.web.controller.form.Form;
import com.trablock.web.controller.form.StateChangeForm;
import com.trablock.web.dto.plan.PlanDirectoryDto;
import com.trablock.web.dto.plan.UserDirectoryDto;
import com.trablock.web.entity.plan.*;
import com.trablock.web.service.plan.*;
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
public class PlanController {

    private final PlanServiceImpl planServiceImpl;
    private final DayServiceImpl dayServiceImpl;
    private final SelectedLocationServiceImpl selectedLocationServiceImpl;
    private final ConceptServiceImpl conceptServiceImpl;
    private final UserDirectoryServiceImpl userDirectoryServiceImpl;
    private final PlanItemServiceImpl planItemServiceImpl;

    //플랜 생성
    @PostMapping("/members/plan")
    public String createPlan(@RequestBody Form form, HttpServletRequest request) {

        Plan plan = planServiceImpl.createPlan(form, request);

        conceptServiceImpl.createConcept(form, request, plan);

        selectedLocationServiceImpl.createSelectedLocation(form, request, plan);

        dayServiceImpl.createDay(form, plan);

        return "redirect:/";
    }

    //main directory get
    @GetMapping("/main-directory")
    public MainDirectory mainPlans(HttpServletRequest request) {
        List<Plan> planDirectoryMain = planServiceImpl.findMainPlanDirectoryMain(request);
        List<PlanDirectoryDto> collect = getPlanDirectoryDtos(planDirectoryMain);

        return new MainDirectory(collect);
    }

    @Data
    @AllArgsConstructor
    static class MainDirectory<T> {

        private T mainDirectory;
    }

    //main-user directory get
    @GetMapping("/main-user-directory")
    public MainUserDirectory usersPlans(HttpServletRequest request) {
        List<UserDirectory> mainUserDirectoryMain = userDirectoryServiceImpl.findMainUserDirectoryMain(request);
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
        List<Plan> planDirectoryMain = planServiceImpl.findTrashPlanDirectoryMain(request);
        List<PlanDirectoryDto> collect = getPlanDirectoryDtos(planDirectoryMain);

        return new TrashDirectory(collect);
    }

    @Data
    @AllArgsConstructor
    static class TrashDirectory<T> {

        private T trashDirectory;
    }

    //user directory get
    @GetMapping("/user-directory/{userDirectoryId}")
    public ShowUserDirectory usersDirectoryPlans(@PathVariable("userDirectoryId") UserDirectory id) {
        List<Plan> userPlanDirectoryUser = planItemServiceImpl.findUserPlanDirectoryUser(id);
        List<PlanDirectoryDto> collect = userPlanDirectoryUser.stream()
                .map(m -> new PlanDirectoryDto(m.getId(), m.getName(), m.getPeriods(), m.getCreatedDate()))
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
            planServiceImpl.cancelPlan(stateChangeForm.getPlanId().get(i));
        }
        return "redirect:/main-directory";
    }

    //플랜 복구(trash -> main)
    @PostMapping("/trash-directory/revert")
    public String revertPlan(@RequestBody StateChangeForm stateChangeForm) {
        for (int i = 0; i < stateChangeForm.getPlanId().size(); i++) {
            planServiceImpl.revertPlan(stateChangeForm.getPlanId().get(i));
        }
        return "redirect:/main-directory";
    }

    //플랜 영구 삭제(trash -> delete)
    @PostMapping("/trash-directory/delete")
    public String deletePlan(@RequestBody StateChangeForm stateChangeForm) {
        for (int i = 0; i < stateChangeForm.getPlanId().size(); i++) {
            planServiceImpl.deletePlan(stateChangeForm.getPlanId().get(i));
        }
        return "redirect:/trash-directory";
    }

    //user directory 생성
    @PostMapping("/create/user-directory")
    public String createUserDirectory(HttpServletRequest request, @RequestBody UserDirectoryForm userDirectoryForm, HttpServletResponse response) {
        userDirectoryServiceImpl.createUserDirectory(request, userDirectoryForm, response);
        return "redirect:/main-directory";
    }

    //user directory 삭제(undelete -> delete)
    @PostMapping("/delete/user-directory")
    public String deleteUserDirectory(@RequestBody UserDirectoryForm userDirectoryForm) {
        for (int i = 0; i < userDirectoryForm.getUserDirectoryId().size(); i++) {
            userDirectoryServiceImpl.deleteUserDirectory(userDirectoryForm.getUserDirectoryId().get(i));
        }
        planItemServiceImpl.deleteMapping(userDirectoryForm);

        return "redirect:/main-directory";
    }

    //plan 이동(main 디렉터리 -> user 디렉터리)
    @PostMapping("/move/user-directory")
    public String moveUserDirectory(@RequestBody MoveDirectoryForm moveDirectoryForm) {
        planItemServiceImpl.moveUserPlan(moveDirectoryForm);
        return "redirect:/main-directory";
    }

    private List<PlanDirectoryDto> getPlanDirectoryDtos(List<Plan> planDirectoryMain) {
        return planDirectoryMain.stream()
                .map(m -> new PlanDirectoryDto(m.getId(), m.getName(), m.getPeriods(), m.getCreatedDate()))
                .collect(Collectors.toList());
    }
}
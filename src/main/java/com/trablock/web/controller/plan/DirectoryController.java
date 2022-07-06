package com.trablock.web.controller.plan;


import com.trablock.web.controller.form.MoveDirectoryForm;
import com.trablock.web.controller.form.StateChangeForm;
import com.trablock.web.controller.form.UserDirectoryForm;
import com.trablock.web.converter.Converter;
import com.trablock.web.dto.plan.DirectoryNameUpdateDto;
import com.trablock.web.dto.plan.PlanDirectoryDto;
import com.trablock.web.dto.plan.UserDirectoryDto;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.UserDirectory;
import com.trablock.web.service.plan.PlanItemService;
import com.trablock.web.service.plan.PlanService;
import com.trablock.web.service.plan.UserDirectoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @GetMapping("/members/plan")
    public Converter.MainDirectory mainPlans(HttpServletRequest request) {
        List<Plan> planDirectoryMain = planService.findMainPlanDirectoryMain(request);
        List<PlanDirectoryDto> collect = getPlanDirectoryDtos(planDirectoryMain);

        int planCount = planService.countPlan(request); // 플랜 갯수 반환
        return new Converter.MainDirectory(planCount, collect);
    }

    //main-user directory get
    @GetMapping("/members/directory")
    public Converter.MainUserDirectory usersPlans(HttpServletRequest request) {
        List<UserDirectory> mainUserDirectoryMain = userDirectoryService.findMainUserDirectoryMain(request);
        List<UserDirectoryDto> collect = mainUserDirectoryMain.stream()
                .map(o -> new UserDirectoryDto(o.getId(), o.getDirectoryName()))
                .collect(Collectors.toList());

        List<UserDirectory> userDirectories = userDirectoryService.findUserDirectory(request);
        List<Integer> planCount = planItemService.countPlan(userDirectories);
        return new Converter.MainUserDirectory(planCount, collect);
    }

    //trash directory get
    @GetMapping("/trash-directory")
    public Converter.TrashDirectory trashPlans(HttpServletRequest request) {
        List<Plan> planDirectoryMain = planService.findTrashPlanDirectoryMain(request);
        List<PlanDirectoryDto> collect = getPlanDirectoryDtos(planDirectoryMain);

        int trashPlanCount = planService.countTrashPlan(request); // 휴지통 플랜 갯수 반환
        return new Converter.TrashDirectory(trashPlanCount, collect);
    }

    // user directory get
    @GetMapping("/user-directory/{userDirectoryId}")
    public Converter.ShowUserDirectory usersDirectoryPlans(@PathVariable("userDirectoryId") UserDirectory id, HttpServletRequest request) {
        List<Plan> userPlanDirectoryUser = planItemService.findUserPlanDirectoryUser(id);
        List<PlanDirectoryDto> collect = userPlanDirectoryUser.stream()
                .map(m -> new PlanDirectoryDto(m.getId(), m.getName(), m.getPeriods(), m.getCreatedDate(), m.getPlanComplete()))
                .collect(Collectors.toList());

        return new Converter.ShowUserDirectory(collect);
    }

    //플랜 삭제(main -> trash)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/main-directory/cancel")
    public String cancelPlan(@RequestBody StateChangeForm stateChangeForm, HttpServletRequest request) {
        for (int i = 0; i < stateChangeForm.getPlanId().size(); i++) {
            planService.cancelPlan(stateChangeForm.getPlanId().get(i), request);
        }
        return "redirect:/main-directory";
    }

    //플랜 복구(trash -> main)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/trash-directory/revert")
    public String revertPlan(@RequestBody StateChangeForm stateChangeForm) {
        for (int i = 0; i < stateChangeForm.getPlanId().size(); i++) {
            planService.revertPlan(stateChangeForm.getPlanId().get(i));
        }
        return "redirect:/main-directory";
    }

    //플랜 영구 삭제(trash -> delete)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/trash-directory/delete")
    public String deletePlan(@RequestBody StateChangeForm stateChangeForm, HttpServletRequest request) {
        for (int i = 0; i < stateChangeForm.getPlanId().size(); i++) {
            planService.deletePlan(stateChangeForm.getPlanId().get(i), request);
        }
        return "redirect:/trash-directory";
    }

    //user directory 생성
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create/user-directory")
    public String createUserDirectory(HttpServletRequest request, @RequestBody UserDirectoryForm userDirectoryForm, HttpServletResponse response) {
        userDirectoryService.createUserDirectory(request, userDirectoryForm, response);
        return "redirect:/main-directory";
    }

    //user directory 삭제(undelete -> delete)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/delete/user-directory")
    public String deleteUserDirectory(@RequestBody UserDirectoryForm userDirectoryForm) {
        for (int i = 0; i < userDirectoryForm.getUserDirectoryId().size(); i++) {
            userDirectoryService.deleteUserDirectory(userDirectoryForm.getUserDirectoryId().get(i));
        }
        planItemService.deleteMapping(userDirectoryForm);

        return "redirect:/main-directory";
    }

    //plan 이동(main 디렉터리 -> user 디렉터리)
    @ResponseStatus(HttpStatus.CREATED)
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
    @ResponseStatus(HttpStatus.CREATED)
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

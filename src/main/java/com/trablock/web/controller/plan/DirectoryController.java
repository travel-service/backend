package com.trablock.web.controller.plan;


import com.trablock.web.controller.form.MoveDirectoryForm;
import com.trablock.web.controller.form.StateChangeForm;
import com.trablock.web.controller.form.UserDirectoryForm;
import com.trablock.web.converter.Converter;
import com.trablock.web.dto.plan.DirectoryNameUpdateDto;
import com.trablock.web.dto.plan.PlanDirectoryDto;
import com.trablock.web.dto.plan.UserDirectoryDto;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.UserDirectory;
import com.trablock.web.service.plan.interfaceC.PlanItemService;
import com.trablock.web.service.plan.interfaceC.PlanService;
import com.trablock.web.service.plan.interfaceC.UserDirectoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DirectoryController {

    private final PlanService planService;
    private final UserDirectoryService userDirectoryService;
    private final PlanItemService planItemService;


    //main directory get
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/directories/main")
    // TODO TEST
    public Converter.MainDirectory mainPlans(HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);

        List<Plan> planDirectoryMain = planService.findMainPlanDirectoryMain(member);
        List<PlanDirectoryDto> collect = getPlanDirectoryDtos(planDirectoryMain);

        int planCount = planService.countPlan(request); // 플랜 갯수 반환
        return new Converter.MainDirectory(planCount, collect);
    }


    //main directory get
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/directories/main/test")
    // TODO TEST
    public Converter.Test test(HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);

        List<Plan> planDirectoryMain = planService.findMainPlanDirectoryMain(member);
        List<PlanDirectoryDto> collect = getPlanDirectoryDtos(planDirectoryMain);

        int planCount = planService.countPlan(request); // 플랜 갯수 반환

        List<List<Long>> test = new ArrayList<>();

        for (Plan plan : planDirectoryMain) {
            List<Long> userDirectoryIds = planItemService.getUserDirectoriesId(plan.getId());

            test.add(userDirectoryIds);
        }

        return new Converter.Test(planCount, test, collect);
    }


    //main-user directory get
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/directories/members")
    // TODO TEST
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
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/directories/trash")
    // TODO TEST
    public Converter.TrashDirectory trashPlans(HttpServletRequest request) {
        List<Plan> planDirectoryMain = planService.findTrashPlanDirectoryMain(request);
        List<PlanDirectoryDto> collect = getPlanDirectoryDtos(planDirectoryMain);

        int trashPlanCount = planService.countTrashPlan(request); // 휴지통 플랜 갯수 반환

        return new Converter.TrashDirectory(trashPlanCount, collect);
    }


    // user directory get
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/directories/{directory-id}")
    // TODO TEST
    public Converter.ShowUserDirectory usersDirectoryPlans(@PathVariable("directory-id") UserDirectory userDirectoryId,
                                                           HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);

        if (member.getId() != null) {
            List<Plan> userPlanDirectoryUser = planItemService.findUserPlanDirectoryUser(userDirectoryId);
            List<PlanDirectoryDto> collect = userPlanDirectoryUser.stream()
                    .map(m -> new PlanDirectoryDto(m.getId(), m.getName(), m.getPeriods(), m.getCreatedDate().toString(), m.getPlanComplete()))
                    .collect(Collectors.toList());

            return new Converter.ShowUserDirectory(collect);
        } else {
            throw new IllegalStateException("가입되지 않은 회원입니다.");
        }
    }


    //플랜 삭제(main -> trash)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/directories/trash")
    // TODO TEST
    public String cancelPlan(@RequestBody StateChangeForm stateChangeForm, HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);

        planService.cancelPlan(stateChangeForm, member);

        return "redirect:/main-directory";
    }


    //플랜 복구(trash -> main)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/directories/main")
    // TODO TEST
    public String revertPlan(@RequestBody StateChangeForm stateChangeForm, HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);

        planService.revertPlan(stateChangeForm, member);

        return "redirect:/main-directory";
    }


    //플랜 영구 삭제(trash -> delete)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("directories/plans")
    // TODO TEST
    public String deletePlan(@RequestBody StateChangeForm stateChangeForm, HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);

        planService.deletePlan(stateChangeForm, member);

        return "redirect:/trash-directory";
    }


    //user directory 생성
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/directories")
    // TODO TEST
    public Long createUserDirectory(HttpServletRequest request,
                                      @RequestBody UserDirectoryForm userDirectoryForm,
                                      HttpServletResponse response) {
        return userDirectoryService.createUserDirectory(request, userDirectoryForm, response);
    }


    //user directory 삭제(undelete -> delete)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/directories/members")
    // TODO TEST
    public String deleteUserDirectory(@RequestBody UserDirectoryForm userDirectoryForm, HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);

        userDirectoryService.deleteUserDirectory(userDirectoryForm, member.getId());
        planItemService.deleteMapping(userDirectoryForm);

        return "redirect:/main-directory";
    }


    //plan 이동(main 디렉터리 -> user 디렉터리)
    @ResponseStatus(HttpStatus.CREATED)
    // TODO TEST
    @PostMapping("/directories/directory/plans")
    public String moveUserDirectory(@RequestBody MoveDirectoryForm moveDirectoryForm, HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);
        planItemService.moveUserPlan(moveDirectoryForm, member.getId());

        return "redirect:/main-directory";
    }


    // user directory 이름 변경
    @ResponseStatus(HttpStatus.CREATED)
    // TODO TEST
    @PostMapping("/directories/{directory-id}/name")
    public void updateUserDirectoryName(@PathVariable("directory-id") Long id,
                                        @RequestBody DirectoryNameUpdateDto directoryNameUpdateDto,
                                        HttpServletRequest request) {

        Member member = planService.getMemberFromPayload(request);

        userDirectoryService.updateDirectoryName(id, directoryNameUpdateDto, member.getId());
    }


    // TODO TEST
    private List<PlanDirectoryDto> getPlanDirectoryDtos(List<Plan> planDirectoryMain) {
        return planDirectoryMain.stream()
                .map(m -> new PlanDirectoryDto(m.getId(), m.getName(), m.getPeriods(), m.getCreatedDate().toString(), m.getPlanComplete()))
                .collect(Collectors.toList());
    }
}

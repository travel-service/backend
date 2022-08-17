package com.trablock.web.controller.plan;


import com.trablock.web.controller.form.MoveDirectoryForm;
import com.trablock.web.controller.form.StateChangeForm;
import com.trablock.web.controller.form.UserDirectoryForm;
import com.trablock.web.dto.plan.DirectoryNameUpdateDto;
import com.trablock.web.dto.plan.PlanDirectoryDto;
import com.trablock.web.dto.plan.PlansDeleteDto;
import com.trablock.web.dto.plan.UserDirectoryDto;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.UserDirectory;
import com.trablock.web.global.HTTPStatus;
import com.trablock.web.service.plan.interfaceC.PlanItemService;
import com.trablock.web.service.plan.interfaceC.PlanService;
import com.trablock.web.service.plan.interfaceC.UserDirectoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.trablock.web.converter.Converter.*;
import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class DirectoryController {

    private final PlanService planService;
    private final UserDirectoryService userDirectoryService;
    private final PlanItemService planItemService;

    @GetMapping("/directories/main")
    public MainDirectory test2(HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);

        int planCount = planService.countPlan(member); // 플랜 갯수 반환

        return planService.findPlanInfo(member, planCount);
    }


    //main-user directory get
    @GetMapping("/directories/members")
    public MainUserDirectory usersPlans(HttpServletRequest request) {

        Member member = planService.getMemberFromPayload(request);

        List<UserDirectory> mainUserDirectoryMain = userDirectoryService.findMainUserDirectoryMain(member.getId());

        List<UserDirectoryDto> collect = mainUserDirectoryMain.stream()
                .map(o -> new UserDirectoryDto(o.getId(), o.getDirectoryName()))
                .collect(toList());

        String message = "모든 사용자 디렉터리를 정상적으로 불러왔습니다.";

        List<UserDirectory> userDirectories = userDirectoryService.findUserDirectory(member.getId());

        List<Integer> planCount = planItemService.countPlan(userDirectories);

        return new MainUserDirectory(HttpStatus.OK.value(), message, planCount, collect);
    }


    //trash directory get
    @GetMapping("/directories/trash")
    public TrashDirectory trashPlans(HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);

        List<Plan> planDirectoryMain = planService.findTrashPlanDirectoryMain(request);
        List<PlanDirectoryDto> collect = getPlanDirectoryDtos(planDirectoryMain);

        int trashPlanCount = planService.countTrashPlan(member); // 휴지통 플랜 갯수 반환

        String message = "휴지통을 정상적으로 불러왔습니다.";

        return new TrashDirectory(HTTPStatus.OK.getCode(), message, trashPlanCount, collect);
    }


    // user directory get
    @GetMapping("/directories/{directoryId}")
    public ShowUserDirectory usersDirectoryPlans(@PathVariable("directoryId") UserDirectory userDirectoryId,
                                                           HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);

        String message = "사용자 디렉터리를 정상적으로 불러왔습니다.";

        if (member.getId() != null) {
            List<Plan> userPlanDirectoryUser = planItemService.findUserPlanDirectoryUser(userDirectoryId);
            List<PlanDirectoryDto> collect = userPlanDirectoryUser.stream()
                    .map(m -> new PlanDirectoryDto(m.getId(), m.getName(), m.getPeriods(), m.getCreatedDate().toString().substring(0, 10), m.getPlanComplete()))
                    .collect(toList());

            return new ShowUserDirectory(HTTPStatus.OK.getCode(), message, collect);
        } else {
            throw new IllegalStateException("가입되지 않은 회원입니다.");
        }
    }


    //플랜 삭제(main -> trash)
    @PostMapping("/directories/trash")
    public PlanMoveMainToTrash cancelPlan(@RequestBody StateChangeForm stateChangeForm, HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);

        planService.cancelPlan(stateChangeForm, member);

        String message = "플랜이 정상적으로 휴지통으로 이동했습니다.";

        return new PlanMoveMainToTrash(HTTPStatus.Created.getCode(), message);
    }


    //플랜 복구(trash -> main)
    @PostMapping("/directories/main")
    public PlanMoveTrashToMain revertPlan(@RequestBody StateChangeForm stateChangeForm, HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);

        planService.revertPlan(stateChangeForm, member);

        String message = "플랜이 정상적으로 복구되었습니다.";

        return new PlanMoveTrashToMain(HTTPStatus.Created.getCode(), message);
    }


    //플랜 영구 삭제(trash -> delete)
    @DeleteMapping("/directories/plans")
    public PlanDelete deletePlan(@RequestBody StateChangeForm stateChangeForm, HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);

        planService.deletePlan(stateChangeForm, member);

        String message = "플랜이 정상적으로 삭제되었습니다.";

        return new PlanDelete(HTTPStatus.NoContent.getCode(), message);
    }


    //user directory 생성
    @PostMapping("/directories")
    public CreateUserDirectory createUserDirectory(HttpServletRequest request,
                                      @RequestBody UserDirectoryForm userDirectoryForm) {
        Member member = planService.getMemberFromPayload(request);

        return userDirectoryService.createUserDirectory(member.getId(), userDirectoryForm);
    }


    //user directory 삭제(undelete -> delete)
    @DeleteMapping("/directories/members")
    public DeleteUserDirectory deleteUserDirectory(@RequestBody UserDirectoryForm userDirectoryForm, HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);

        userDirectoryService.deleteUserDirectory(userDirectoryForm, member.getId());

        planItemService.deleteMapping(userDirectoryForm);

        String message = "디렉터리가 정상적으로 삭제되었습니다.";

        return new DeleteUserDirectory(HTTPStatus.NoContent.getCode(), message);
    }


    //plan 이동(main 디렉터리 -> user 디렉터리)
    @PostMapping("/directories/directory/plans")
    public PlanMoveToUserDirectory moveUserDirectory(@RequestBody MoveDirectoryForm moveDirectoryForm, HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);

        return planItemService.moveUserPlan(moveDirectoryForm, member.getId());
    }


    // user directory 이름 변경
    @PostMapping("/directories/{directoryId}/name")
    public UpdatePlanName updateUserDirectoryName(@PathVariable("directoryId") Long id,
                                        @RequestBody DirectoryNameUpdateDto directoryNameUpdateDto,
                                        HttpServletRequest request) {

        Member member = planService.getMemberFromPayload(request);

        return userDirectoryService.updateDirectoryName(id, directoryNameUpdateDto, member.getId());
    }

    // user directory 플랜 삭제
    @DeleteMapping("directories/{directoryId}/plans")
    public DeletePlans deletePlans(@PathVariable("directoryId") Long userDirectoryId,
                                   @RequestBody PlansDeleteDto plansDeleteDto,
                                   HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);

        return planItemService.deletePlans(userDirectoryId, plansDeleteDto);
    }


    private List<PlanDirectoryDto> getPlanDirectoryDtos(List<Plan> planDirectoryMain) {
        return planDirectoryMain.stream()
                .map(m -> new PlanDirectoryDto(m.getId(), m.getName(), m.getPeriods(), m.getCreatedDate().toString().substring(0, 10), m.getPlanComplete()))
                .collect(toList());
    }
}

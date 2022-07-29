package com.trablock.web.service.plan;

import com.trablock.web.controller.form.Form;
import com.trablock.web.controller.form.PlanForm;
import com.trablock.web.controller.form.StateChangeForm;
import com.trablock.web.dto.plan.PlanDto;
import com.trablock.web.dto.plan.UserPlanUpdateDto;
import com.trablock.web.entity.member.Gender;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.member.MemberInfo;
import com.trablock.web.entity.member.MemberProfile;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.enumtype.PlanComplete;
import com.trablock.web.entity.plan.enumtype.PlanStatus;
import com.trablock.web.repository.member.MemberRepository;
import com.trablock.web.repository.plan.PlanRepository;
import com.trablock.web.service.plan.interfaceC.PlanService;
import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PlanServiceTest {

    @Autowired
    PlanService planService;

    @Autowired
    PlanRepository planRepository;

    @Autowired
    MemberRepository memberRepository;

    Plan plan;
    Member member;

    @BeforeEach
    void init() {
        Member initMember = new Member("username", "1234",
                new MemberProfile("nickname", "bio"),
                new MemberInfo("19980102", Gender.MALE, "wkdwoo@kakao.com"),
                new ArrayList<>(), true);

        member = memberRepository.save(initMember);
    }


    @Test
    @DisplayName("Plan 저장 테스트")
    public void savePlanTest() throws Exception {
        //given

        //when
        plan = Plan.builder()
                .depart("test-depart")
                .name("test-name")
                .planComplete(PlanComplete.UNFINISHED)
                .planStatus(PlanStatus.MAIN)
                .thumbnail("test-thumbnail")
                .periods(1)
                .member(member)
                .build();

        planService.savePlan(plan);

        //then
        assertEquals(plan.getPlanStatus(), PlanStatus.MAIN);
        assertEquals(plan.getMember(), member);
        assertEquals(plan.getName(), "test-name");
        assertEquals(plan.getDepart(), "test-depart");
        assertEquals(plan.getThumbnail(), "test-thumbnail");
        assertEquals(plan.getPeriods(), 1);
        assertEquals(plan.getPlanComplete(), PlanComplete.UNFINISHED);
        assertEquals(plan.getMember().getId(), member.getId());
    }

    @Test
    @DisplayName("plan 생성 test")
    void createPlanTest() throws Exception {
        //given
        Form form = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .name("test-name")
                                .planComplete(PlanComplete.UNFINISHED)
                                .planStatus(PlanStatus.MAIN)
                                .thumbnail("test-thumbnail")
                                .periods(1)
                                .build()
                ).build();

        //when
        Plan plan = planService.createPlan(form, member);

        //then
        assertEquals(plan.getPlanStatus(), PlanStatus.MAIN);
        assertEquals(plan.getMember(), member);
        assertEquals(plan.getName(), "test-name");
        assertEquals(plan.getDepart(), "test-depart");
        assertEquals(plan.getThumbnail(), "test-thumbnail");
        assertEquals(plan.getPeriods(), 1);
        assertEquals(plan.getPlanComplete(), PlanComplete.UNFINISHED);
    }


    @Test
    @DisplayName("하나의 plan dto를 받아오는 test")
    public void getOnePlanDtoTest() throws Exception {
        //given
        Form form = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .name("test-name")
                                .periods(1)
                                .build()
                ).build();

        Plan plan = planService.createPlan(form, member);

        //when
        PlanDto onePlanDto = planService.getOnePlanDto(plan.getId(), member);

        //then
        assertEquals(onePlanDto.getPlanId(), plan.getId());
        assertEquals(onePlanDto.getPeriods(), plan.getPeriods());
        assertEquals(onePlanDto.getDepart(), plan.getDepart());
        assertEquals(onePlanDto.getName(), plan.getName());
     }

    @Test
    @DisplayName("main 디렉터리 정보 GET test")
    public void findMainPlanDirectoryMainTest() throws Exception {
        //given
        Form form1 = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .name("test-name")
                                .planStatus(PlanStatus.MAIN)
                                .periods(1)
                                .build()
                ).build();

        Form form2 = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .planStatus(PlanStatus.MAIN)
                                .name("test-name")
                                .periods(1)
                                .build()
                ).build();

        Form form3 = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .name("test-name")
                                .planStatus(PlanStatus.MAIN)
                                .periods(1)
                                .build()
                ).build();

        planService.createPlan(form1, member);
        planService.createPlan(form2, member);
        planService.createPlan(form3, member);

        //when
        List<Plan> mainPlanDirectoryMain = planService.findMainPlanDirectoryMain(member);

        //then
        assertEquals(mainPlanDirectoryMain.size(), 3);
    }

    @Test
    @DisplayName("사용자가 플랜을 휴지통으로 버릴 시 PlanStatus가 MAIN -> TRASH로 update 되는지 test")
    public void cancelPlanTest() throws Exception {
        //given
        Form form1 = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .name("test-name")
                                .planStatus(PlanStatus.MAIN)
                                .periods(1)
                                .build()
                ).build();

        Form form2 = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .planStatus(PlanStatus.MAIN)
                                .name("test-name")
                                .periods(1)
                                .build()
                ).build();

        Form form3 = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .name("test-name")
                                .planStatus(PlanStatus.MAIN)
                                .periods(1)
                                .build()
                ).build();

        Plan plan1 = planService.createPlan(form1, member);
        Plan plan2 = planService.createPlan(form2, member);
        Plan plan3 = planService.createPlan(form3, member);

        List<Long> planIds = new ArrayList<>();

        planIds.add(plan1.getId());
        planIds.add(plan2.getId());

        StateChangeForm stateChangeForm = StateChangeForm.builder()
                .planId(planIds)
                .build();

        //when
        planService.cancelPlan(stateChangeForm, member);

        //then
        assertEquals(plan1.getPlanStatus(), PlanStatus.TRASH);
        assertEquals(plan2.getPlanStatus(), PlanStatus.TRASH);
        assertEquals(plan3.getPlanStatus(), PlanStatus.MAIN);
    }

    @Test
    @DisplayName("사용자가 플랜을 휴지통으로 버릴 시 PlanStatus가 MAIN -> TRASH로 update 되지 않는 예외 상황 test")
    public void cancelPlanExceptionTest() throws Exception {
        //given
        Form form1 = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .name("test-name")
                                .planStatus(PlanStatus.TRASH)
                                .periods(1)
                                .build()
                ).build();

        Form form2 = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .planStatus(PlanStatus.TRASH)
                                .name("test-name")
                                .periods(1)
                                .build()
                ).build();

        Form form3 = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .name("test-name")
                                .planStatus(PlanStatus.DELETE)
                                .periods(1)
                                .build()
                ).build();

        Plan plan1 = planService.createPlan(form1, member);
        Plan plan2 = planService.createPlan(form2, member);
        Plan plan3 = planService.createPlan(form3, member);

        List<Long> planIds1 = new ArrayList<>();

        planIds1.add(plan1.getId());
        planIds1.add(plan2.getId());

        StateChangeForm stateChangeForm1 = StateChangeForm.builder()
                .planId(planIds1)
                .build();

        List<Long> planIds2 = new ArrayList<>();

        planIds2.add(plan3.getId());

        StateChangeForm stateChangeForm2 = StateChangeForm.builder()
                .planId(planIds2)
                .build();

        //when
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> planService.cancelPlan(stateChangeForm1, member));

        assertThat(e.getMessage()).isEqualTo("이미 휴지통으로 이동된 플랜입니다.");

        IllegalStateException e2 = assertThrows(IllegalStateException.class, () -> planService.cancelPlan(stateChangeForm2, member));

        assertThat(e2.getMessage()).isEqualTo("삭제된 플랜입니다.");
    }

    @Test
    @DisplayName("사용자가 플랜을 완전 삭제 시 PlanStatus가 TRASH -> DELETE 로 update 되는지 test")
    public void deletePlanTest() throws Exception {
        //given
         Form form1 = Form.builder()
                 .planForm(
                         PlanForm.builder()
                                 .depart("test-depart")
                                 .name("test-name")
                                 .planStatus(PlanStatus.MAIN)
                                 .periods(1)
                                 .build()
                 ).build();

         Form form2 = Form.builder()
                 .planForm(
                         PlanForm.builder()
                                 .depart("test-depart")
                                 .planStatus(PlanStatus.MAIN)
                                 .name("test-name")
                                 .periods(1)
                                 .build()
                 ).build();

         Form form3 = Form.builder()
                 .planForm(
                         PlanForm.builder()
                                 .depart("test-depart")
                                 .name("test-name")
                                 .planStatus(PlanStatus.MAIN)
                                 .periods(1)
                                 .build()
                 ).build();

         Plan plan1 = planService.createPlan(form1, member);
         Plan plan2 = planService.createPlan(form2, member);
         Plan plan3 = planService.createPlan(form3, member);

         List<Long> planIds = new ArrayList<>();

         planIds.add(plan1.getId());
         planIds.add(plan2.getId());

         StateChangeForm stateChangeForm = StateChangeForm.builder()
                 .planId(planIds)
                 .build();

         //when
         planService.cancelPlan(stateChangeForm, member);

         planService.deletePlan(stateChangeForm, member);

         //then
         assertEquals(plan1.getPlanStatus(), PlanStatus.DELETE);
         assertEquals(plan2.getPlanStatus(), PlanStatus.DELETE);
         assertEquals(plan3.getPlanStatus(), PlanStatus.MAIN);
    }


    @Test
    @DisplayName("사용자가 플랜을 완전 삭제 시 PlanStatus가 TRASH -> DELETE 로 update 되는지 않는 예외 상황 test")
    public void deletePlanExceptionTest() throws Exception {
        //given
        Form form1 = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .name("test-name")
                                .planStatus(PlanStatus.DELETE)
                                .periods(1)
                                .build()
                ).build();

        Form form2 = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .planStatus(PlanStatus.DELETE)
                                .name("test-name")
                                .periods(1)
                                .build()
                ).build();

        Form form3 = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .name("test-name")
                                .planStatus(PlanStatus.MAIN)
                                .periods(1)
                                .build()
                ).build();

        Plan plan1 = planService.createPlan(form1, member);
        Plan plan2 = planService.createPlan(form2, member);
        Plan plan3 = planService.createPlan(form3, member);

        List<Long> planIds1 = new ArrayList<>();

        planIds1.add(plan1.getId());
        planIds1.add(plan2.getId());

        StateChangeForm stateChangeForm1 = StateChangeForm.builder()
                .planId(planIds1)
                .build();

        List<Long> planIds2 = new ArrayList<>();

        planIds2.add(plan3.getId());

        StateChangeForm stateChangeForm2 = StateChangeForm.builder()
                .planId(planIds2)
                .build();



        //when
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> planService.deletePlan(stateChangeForm1, member));

        assertThat(e.getMessage()).isEqualTo("이미 삭제된 플랜입니다.");

        IllegalStateException e2 = assertThrows(IllegalStateException.class, () -> planService.deletePlan(stateChangeForm2, member));

        assertThat(e2.getMessage()).isEqualTo("휴지통으로 먼저 이동하시오");
    }

    @Test
    @DisplayName("사용자가 플랜을 복구 시 PlanStatus가 TRASH -> MAIN 로 update 되는지 test")
    public void revertPlanTest() throws Exception {
         //given
         Form form1 = Form.builder()
                 .planForm(
                         PlanForm.builder()
                                 .depart("test-depart")
                                 .name("test-name")
                                 .planStatus(PlanStatus.MAIN)
                                 .periods(1)
                                 .build()
                 ).build();

         Form form2 = Form.builder()
                 .planForm(
                         PlanForm.builder()
                                 .depart("test-depart")
                                 .planStatus(PlanStatus.MAIN)
                                 .name("test-name")
                                 .periods(1)
                                 .build()
                 ).build();

         Form form3 = Form.builder()
                 .planForm(
                         PlanForm.builder()
                                 .depart("test-depart")
                                 .name("test-name")
                                 .planStatus(PlanStatus.MAIN)
                                 .periods(1)
                                 .build()
                 ).build();

         Plan plan1 = planService.createPlan(form1, member);
         Plan plan2 = planService.createPlan(form2, member);
         Plan plan3 = planService.createPlan(form3, member);

         List<Long> planIds1 = new ArrayList<>();
         List<Long> planIds2 = new ArrayList<>();

         planIds1.add(plan1.getId());
         planIds1.add(plan2.getId());
         planIds1.add(plan3.getId());

         StateChangeForm stateChangeForm1 = StateChangeForm.builder()
                 .planId(planIds1)
                 .build();

        planIds2.add(plan1.getId());
        planIds2.add(plan2.getId());

         StateChangeForm stateChangeForm2 = StateChangeForm.builder()
                 .planId(planIds2)
                 .build();

         //when
         planService.cancelPlan(stateChangeForm1, member);

         planService.revertPlan(stateChangeForm2, member);

         //then
         assertEquals(plan1.getPlanStatus(), PlanStatus.MAIN);
         assertEquals(plan2.getPlanStatus(), PlanStatus.MAIN);
         assertEquals(plan3.getPlanStatus(), PlanStatus.TRASH);
    }

    @Test
    @DisplayName("사용자가 플랜을 복구 시 PlanStatus가 TRASH -> MAIN 로 update 되지 않는 예외 상황 test")
    public void revertPlanExceptionTest() throws Exception {
        //given
        Form form1 = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .name("test-name")
                                .planStatus(PlanStatus.MAIN)
                                .periods(1)
                                .build()
                ).build();

        Form form2 = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .planStatus(PlanStatus.MAIN)
                                .name("test-name")
                                .periods(1)
                                .build()
                ).build();

        Form form3 = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .name("test-name")
                                .planStatus(PlanStatus.DELETE)
                                .periods(1)
                                .build()
                ).build();

        Plan plan1 = planService.createPlan(form1, member);
        Plan plan2 = planService.createPlan(form2, member);
        Plan plan3 = planService.createPlan(form3, member);

        List<Long> planIds1 = new ArrayList<>();
        List<Long> planIds2 = new ArrayList<>();

        planIds1.add(plan1.getId());
        planIds1.add(plan2.getId());

        StateChangeForm stateChangeForm1 = StateChangeForm.builder()
                .planId(planIds1)
                .build();

        planIds2.add(plan3.getId());

        StateChangeForm stateChangeForm2 = StateChangeForm.builder()
                .planId(planIds2)
                .build();

        //when
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> planService.revertPlan(stateChangeForm1, member));

        assertThat(e.getMessage()).isEqualTo("이미 복구된 플랜입니다.");

        IllegalStateException e2 = assertThrows(IllegalStateException.class, () -> planService.revertPlan(stateChangeForm2, member));

        assertThat(e2.getMessage()).isEqualTo("완전 삭제된 플랜은 복구 할 수 없습니다.");
    }


    @Test
    @DisplayName("플랜 완성시 PLanComplete status 가 UNFINISHED -> FINISHED 로 변경되는 지 test")
    public void finishedPlanTest() throws Exception {
        //given
        Form form = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .name("test-name")
                                .planComplete(PlanComplete.UNFINISHED)
                                .planStatus(PlanStatus.MAIN)
                                .thumbnail("test-thumbnail")
                                .periods(1)
                                .build()
                ).build();

        Plan plan = planService.createPlan(form, member);

        //when
        planService.finishedPlan(plan.getId());

        //then
        assertEquals(plan.getPlanComplete(), PlanComplete.FINISHED);
    }

    @Test
    @DisplayName("플랜 수정시 PLanComplete status 가 FINISHED -> UNFINISHED 로 변경되는 지 test")
    public void unFinishedPlanTest() throws Exception {
        //given
        Form form = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .name("test-name")
                                .planComplete(PlanComplete.UNFINISHED)
                                .planStatus(PlanStatus.MAIN)
                                .thumbnail("test-thumbnail")
                                .periods(1)
                                .build()
                ).build();

        Plan plan = planService.createPlan(form, member);

        planService.finishedPlan(plan.getId());

        //when
        planService.unFinishedPlan(plan.getId());

        //then
        assertEquals(plan.getPlanComplete(), PlanComplete.UNFINISHED);
    }

    @Test
    @DisplayName("플랜 완성시 PLanComplete status 가 UNFINISHED -> FINISHED 로 변경되지 않는 예외 상황 test")
    public void UnFinishedPlanExceptionTest() throws Exception {
        //given
        Form form = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .name("test-name")
                                .planComplete(PlanComplete.UNFINISHED)
                                .planStatus(PlanStatus.MAIN)
                                .thumbnail("test-thumbnail")
                                .periods(1)
                                .build()
                ).build();

        Plan plan = planService.createPlan(form, member);

        //when
        planService.finishedPlan(plan.getId());

        //then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> planService.finishedPlan(plan.getId()));

        assertThat(e.getMessage()).isEqualTo("이미 완성된 플랜입니다.");
    }

    @Test
    @DisplayName("플랜 수정 test")
    public void updateUserPlanContentTest() throws Exception {
        //given
        Form form = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .name("test-name")
                                .planComplete(PlanComplete.UNFINISHED)
                                .planStatus(PlanStatus.MAIN)
                                .thumbnail("test-thumbnail")
                                .periods(1)
                                .build()
                ).build();

        Plan plan = planService.createPlan(form, member);

        //when

        UserPlanUpdateDto userPlanUpdateDto = UserPlanUpdateDto.builder()
                .depart("depart-update")
                .name("name-update")
                .periods(2)
                .thumbnail("thumbnail-update")
                .build();

        planService.updateUserPlanContent(plan.getId(), member, userPlanUpdateDto);

        //then
        assertEquals(plan.getDepart(), "depart-update");
        assertEquals(plan.getName(), "name-update");
        assertEquals(plan.getPeriods(), 2);
        assertEquals(plan.getThumbnail(), "thumbnail-update");
        assertEquals(plan.getPlanStatus(), PlanStatus.MAIN);
        assertEquals(plan.getPlanComplete(), PlanComplete.UNFINISHED);
        assertEquals(plan.getConcepts(), null);
    }

    @Test
    @DisplayName("사용자가 만든 플랜 갯수 불러오는 test")
    public void countPlanTest() throws Exception {
        //given
        Form form1 = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .name("test-name")
                                .planStatus(PlanStatus.MAIN)
                                .periods(1)
                                .build()
                ).build();

        Form form2 = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .planStatus(PlanStatus.MAIN)
                                .name("test-name")
                                .periods(1)
                                .build()
                ).build();

        Form form3 = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .name("test-name")
                                .planStatus(PlanStatus.MAIN)
                                .periods(1)
                                .build()
                ).build();

        planService.createPlan(form1, member);
        planService.createPlan(form2, member);
        planService.createPlan(form3, member);

        //when
        int countPlan = planService.countPlan(member);

        //then
        assertEquals(countPlan, 3);
    }


    @Test
    @DisplayName("휴지통에 있는 플랜 갯수 불러오는 test")
    public void countTrashPlanTest() throws Exception {
        //given
        Form form1 = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .name("test-name")
                                .planStatus(PlanStatus.MAIN)
                                .periods(1)
                                .build()
                ).build();

        Form form2 = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .planStatus(PlanStatus.MAIN)
                                .name("test-name")
                                .periods(1)
                                .build()
                ).build();

        Form form3 = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .name("test-name")
                                .planStatus(PlanStatus.MAIN)
                                .periods(1)
                                .build()
                ).build();

        Plan plan1 = planService.createPlan(form1, member);
        Plan plan2 = planService.createPlan(form2, member);
        Plan plan3 = planService.createPlan(form3, member);

        List<Long> planIds = new ArrayList<>();

        planIds.add(plan1.getId());
        planIds.add(plan2.getId());

        StateChangeForm stateChangeForm = StateChangeForm.builder()
                .planId(planIds)
                .build();

        planService.cancelPlan(stateChangeForm, member);

        //when
        int countTrashPlan = planService.countTrashPlan(member);

        //then
        assertEquals(countTrashPlan, 2);
    }

    @Test
    @DisplayName("SelectedLocation의 locationId를 불러오기 위한 Plan 객체 가져오는 test")
    public void returnPlanTest() throws Exception {
        //given
        Form form = Form.builder()
                .planForm(
                        PlanForm.builder()
                                .depart("test-depart")
                                .name("test-name")
                                .planComplete(PlanComplete.UNFINISHED)
                                .planStatus(PlanStatus.MAIN)
                                .thumbnail("test-thumbnail")
                                .periods(1)
                                .build()
                ).build();

        Plan plan = planService.createPlan(form, member);

        //when
        Plan returnPlan = planService.returnPlan(plan.getId(), member);

        //then
        assertEquals(returnPlan.getId(), plan.getId());
        assertThat(returnPlan.getId()).isEqualTo(plan.getId());
    }
}
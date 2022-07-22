package com.trablock.web.service.plan;

import com.trablock.web.controller.form.Form;
import com.trablock.web.controller.form.PlanForm;
import com.trablock.web.controller.form.StateChangeForm;
import com.trablock.web.dto.plan.PlanDto;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
}
package com.trablock.web.service.plan;

import com.trablock.web.controller.form.Form;
import com.trablock.web.controller.form.PlanForm;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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


}
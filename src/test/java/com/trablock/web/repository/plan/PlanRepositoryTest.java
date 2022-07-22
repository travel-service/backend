package com.trablock.web.repository.plan;

import com.trablock.web.entity.member.Gender;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.member.MemberInfo;
import com.trablock.web.entity.member.MemberProfile;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.enumtype.PlanComplete;
import com.trablock.web.entity.plan.enumtype.PlanStatus;
import com.trablock.web.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PlanRepositoryTest {

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

        plan = Plan.builder()
                .member(member)
                .days(new ArrayList<>())
                .concepts(new ArrayList<>())
                .planItems(new ArrayList<>())
                .name("load to paris")
                .planStatus(PlanStatus.MAIN)
                .planComplete(PlanComplete.UNFINISHED)
                .build();
    }

    @Test
    @DisplayName("Plan 저장 테스트")
    void save() throws Exception {
        //given

        //when
        Plan target = planRepository.save(plan);

        //then
        assertThat(target.getMember().getId()).isEqualTo(member.getId());
        assertThat(target.getName()).isEqualTo("load to paris");
        assertThat(target.getPlanStatus()).isEqualTo(PlanStatus.MAIN);
        assertThat(target.getPlanComplete()).isEqualTo(PlanComplete.UNFINISHED);
    }

    @Test
    @DisplayName("id로 플랜 조회")
    void findPlanById() throws Exception {

        //given
        Long planId = planRepository.save(plan).getId();

        //when
        Plan target = planRepository.findPlanById(planId).orElseThrow();

        //then
        assertThat(target.getId()).isEqualTo(planId);
        assertThat(target.getMember().getId()).isEqualTo(plan.getMember().getId());
        assertThat(target.getName()).isEqualTo(plan.getName());
        assertThat(target.getPlanComplete()).isEqualTo(plan.getPlanComplete());
        assertThat(target.getPlanStatus()).isEqualTo(plan.getPlanStatus());
    }

    @Test
    void findPlanByMember() throws Exception {
        //given
        Long planId = planRepository.save(plan).getId();

        //when
        Plan target = planRepository.findPlanByMember(planId, member).orElseThrow();

        //then
        assertThat(target.getId()).isEqualTo(planId);
        assertThat(target.getMember().getId()).isEqualTo(plan.getMember().getId());
        assertThat(target.getName()).isEqualTo(plan.getName());
        assertThat(target.getPlanComplete()).isEqualTo(plan.getPlanComplete());
        assertThat(target.getPlanStatus()).isEqualTo(plan.getPlanStatus());
    }

    @Test
    void findPlansByStatus_MAIN() throws Exception {
        //given
        Plan mainPlan1 = Plan.builder()
                .member(member)
                .days(new ArrayList<>())
                .concepts(new ArrayList<>())
                .planItems(new ArrayList<>())
                .name("one")
                .planStatus(PlanStatus.MAIN)
                .planComplete(PlanComplete.UNFINISHED)
                .build();

        Plan mainPlan2 = Plan.builder()
                .member(member)
                .days(new ArrayList<>())
                .concepts(new ArrayList<>())
                .planItems(new ArrayList<>())
                .name("two")
                .planStatus(PlanStatus.MAIN)
                .planComplete(PlanComplete.UNFINISHED)
                .build();

        Plan mainPlan3 = Plan.builder()
                .member(member)
                .days(new ArrayList<>())
                .concepts(new ArrayList<>())
                .planItems(new ArrayList<>())
                .name("three")
                .planStatus(PlanStatus.MAIN)
                .planComplete(PlanComplete.UNFINISHED)
                .build();

        planRepository.save(mainPlan1);
        planRepository.save(mainPlan2);
        planRepository.save(mainPlan3);

        //when
        List<Plan> plansByPlanStatus = planRepository.findPlansByPlanStatus(member, PlanStatus.MAIN);

        //then
        plansByPlanStatus.forEach(byPlanStatus -> assertThat(byPlanStatus.getPlanStatus()).isEqualTo(PlanStatus.MAIN));
    }

    @Test
    void findPlansByStatus_DELETE() throws Exception {
        //given
        Plan deletePlan1 = Plan.builder()
                .member(member)
                .days(new ArrayList<>())
                .concepts(new ArrayList<>())
                .planItems(new ArrayList<>())
                .name("one")
                .planStatus(PlanStatus.DELETE)
                .planComplete(PlanComplete.UNFINISHED)
                .build();

        Plan deletePlan2 = Plan.builder()
                .member(member)
                .days(new ArrayList<>())
                .concepts(new ArrayList<>())
                .planItems(new ArrayList<>())
                .name("two")
                .planStatus(PlanStatus.DELETE)
                .planComplete(PlanComplete.UNFINISHED)
                .build();

        Plan deletePlan3 = Plan.builder()
                .member(member)
                .days(new ArrayList<>())
                .concepts(new ArrayList<>())
                .planItems(new ArrayList<>())
                .name("three")
                .planStatus(PlanStatus.DELETE)
                .planComplete(PlanComplete.UNFINISHED)
                .build();

        planRepository.save(deletePlan1);
        planRepository.save(deletePlan2);
        planRepository.save(deletePlan3);

        //when
        List<Plan> plansByPlanStatus = planRepository.findPlansByPlanStatus(member, PlanStatus.DELETE);

        //then
        plansByPlanStatus.forEach(byPlanStatus -> assertThat(byPlanStatus.getPlanStatus()).isEqualTo(PlanStatus.DELETE));
    }

}
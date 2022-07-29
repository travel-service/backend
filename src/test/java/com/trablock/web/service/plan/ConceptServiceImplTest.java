package com.trablock.web.service.plan;

import com.trablock.web.entity.member.Gender;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.member.MemberInfo;
import com.trablock.web.entity.member.MemberProfile;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.enumtype.PlanComplete;
import com.trablock.web.entity.plan.enumtype.PlanStatus;
import com.trablock.web.repository.member.MemberRepository;
import com.trablock.web.repository.plan.ConceptRepository;
import com.trablock.web.repository.plan.PlanRepository;
import com.trablock.web.service.plan.interfaceC.ConceptService;
import com.trablock.web.service.plan.interfaceC.PlanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ConceptServiceImplTest {

    @Autowired ConceptService conceptService;
    @Autowired ConceptRepository conceptRepository;
    @Autowired PlanService planService;
    @Autowired MemberRepository memberRepository;
    @Autowired PlanRepository planRepository;

    Plan plan;
    Member member;

    @BeforeEach
    void init() {
        Member initMember = new Member("username", "1234",
                new MemberProfile("nickname", "bio"),
                new MemberInfo("19980102", Gender.MALE, "wkdwoo@kakao.com"),
                new ArrayList<>(), true);

        member = memberRepository.save(initMember);

        Plan plan1 = Plan.builder()
                .depart("test-depart-1")
                .name("test-name-1")
                .planComplete(PlanComplete.UNFINISHED)
                .planStatus(PlanStatus.MAIN)
                .thumbnail("test-thumbnail-1")
                .periods(1)
                .member(member)
                .build();

        Plan plan2 = Plan.builder()
                .depart("test-depart-2")
                .name("test-name-2")
                .planComplete(PlanComplete.UNFINISHED)
                .planStatus(PlanStatus.MAIN)
                .thumbnail("test-thumbnail-2")
                .periods(2)
                .member(member)
                .build();

        Plan plan3 = Plan.builder()
                .depart("test-depart-3")
                .name("test-name-3")
                .planComplete(PlanComplete.UNFINISHED)
                .planStatus(PlanStatus.MAIN)
                .thumbnail("test-thumbnail-3")
                .periods(3)
                .member(member)
                .build();

        planService.savePlan(plan1);
        planService.savePlan(plan2);
        planService.savePlan(plan3);
    }


    @Test
    @DisplayName("concept 생성 test")
    public void createConceptTest() throws Exception {
        //given



        //when

        //then
     }
}
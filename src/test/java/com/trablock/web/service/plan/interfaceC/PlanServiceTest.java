package com.trablock.web.service.plan.interfaceC;

import com.trablock.web.entity.member.Gender;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.member.MemberInfo;
import com.trablock.web.entity.member.MemberProfile;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.enumtype.PlanComplete;
import com.trablock.web.entity.plan.enumtype.PlanStatus;
import com.trablock.web.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
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
    void savePlan() throws Exception {
        //given
        planService.savePlan(plan);

        //when

        //then

    }

}
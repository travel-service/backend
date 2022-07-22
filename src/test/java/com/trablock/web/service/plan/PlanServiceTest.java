package com.trablock.web.service.plan;

import com.trablock.web.controller.form.Form;
import com.trablock.web.controller.form.PlanForm;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

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

    @Test
    @DisplayName("plan 생성 test")
    void createPlan() throws Exception {
        //given
        Member initMember = new Member("username", "1234",
                new MemberProfile("nickname", "bio"),
                new MemberInfo("19980102", Gender.MALE, "wkdwoo@kakao.com"),
                new ArrayList<>(), true);

        member = memberRepository.save(initMember);

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

}
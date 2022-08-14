package com.trablock.web.service.plan;

import com.trablock.web.controller.form.MoveDirectoryForm;
import com.trablock.web.entity.member.Gender;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.member.MemberInfo;
import com.trablock.web.entity.member.MemberProfile;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.UserDirectory;
import com.trablock.web.entity.plan.enumtype.PlanComplete;
import com.trablock.web.entity.plan.enumtype.PlanStatus;
import com.trablock.web.repository.member.MemberRepository;
import com.trablock.web.repository.plan.PlanItemRepository;
import com.trablock.web.repository.plan.PlanRepository;
import com.trablock.web.repository.plan.UserDirectoryRepository;
import com.trablock.web.service.plan.interfaceC.PlanItemService;
import com.trablock.web.service.plan.interfaceC.UserDirectoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.trablock.web.converter.Converter.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PlanItemServiceImplTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PlanRepository planRepository;

    @Autowired
    PlanItemService planItemService;

    @Autowired
    PlanItemRepository planItemRepository;

    @Autowired
    UserDirectoryService userDirectoryService;

    @Autowired
    UserDirectoryRepository userDirectoryRepository;

    Plan testPlan1;
    Plan testPlan2;
    Plan testPlan3;
    Member member;
    UserDirectory testUserDirectory1;
    UserDirectory testUserDirectory2;

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

        testPlan1 = planRepository.save(plan1);

        Plan plan2 = Plan.builder()
                .depart("test-depart-1")
                .name("test-name-1")
                .planComplete(PlanComplete.UNFINISHED)
                .planStatus(PlanStatus.MAIN)
                .thumbnail("test-thumbnail-1")
                .periods(1)
                .member(member)
                .build();

        testPlan2 = planRepository.save(plan2);

        Plan plan3 = Plan.builder()
                .depart("test-depart-1")
                .name("test-name-1")
                .planComplete(PlanComplete.UNFINISHED)
                .planStatus(PlanStatus.MAIN)
                .thumbnail("test-thumbnail-1")
                .periods(1)
                .member(member)
                .build();

        testPlan3 = planRepository.save(plan3);


        UserDirectory userDirectory1 = UserDirectory.builder()
                .directoryName("제주1")
                .member(member)
                .build();

        testUserDirectory1 = userDirectoryRepository.save(userDirectory1);

        UserDirectory userDirectory2 = UserDirectory.builder()
                .directoryName("제주2")
                .member(member)
                .build();

        testUserDirectory2 = userDirectoryRepository.save(userDirectory2);
    }

    @Test
    @DisplayName("유저가 만든플랜을 main 디렉터리에서 -> user 디렉터리로 이동 test")
    public void moveUserPlanTest() throws Exception {
        //given
        List<Long> planIds = new ArrayList<>();

        planIds.add(testPlan1.getId());
        planIds.add(testPlan2.getId());
        planIds.add(testPlan3.getId());

        MoveDirectoryForm moveDirectoryForm = MoveDirectoryForm.builder()
                .userDirectoryId(testUserDirectory1.getId())
                .planId(planIds)
                .build();

        //when
        PlanMoveToUserDirectory planMoveToUserDirectory = planItemService.moveUserPlan(moveDirectoryForm, member.getId());

        //then
        String message = "플랜이 정상적으로 디렉터리로 이동되었습니다.";

        assertThat(planMoveToUserDirectory.getHttpStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(planMoveToUserDirectory.getMessage()).isEqualTo(message);

    }

    @Test
    @DisplayName("사용자가 만든 디렉터리 안에 플랜을 List으로 받아오는지 test")
    public void findUserPlanDirectoryUserTest() throws Exception {
        //given
        List<Long> planIds = new ArrayList<>();

        planIds.add(testPlan1.getId());
        planIds.add(testPlan2.getId());

        MoveDirectoryForm moveDirectoryForm = MoveDirectoryForm.builder()
                .userDirectoryId(testUserDirectory1.getId())
                .planId(planIds)
                .build();

        planItemService.moveUserPlan(moveDirectoryForm, member.getId());

        //when
        List<Plan> userPlanUserDirectory = planItemService.findUserPlanDirectoryUser(testUserDirectory1);

        //then
        assertThat(userPlanUserDirectory.get(0).getId()).isEqualTo(testPlan1.getId());
        assertThat(userPlanUserDirectory.get(1).getId()).isEqualTo(testPlan2.getId());
    }
}
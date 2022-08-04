package com.trablock.web.service.plan;

import com.trablock.web.controller.form.ConceptForm;
import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.member.Gender;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.member.MemberInfo;
import com.trablock.web.entity.member.MemberProfile;
import com.trablock.web.entity.plan.Concept;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.enumtype.PlanComplete;
import com.trablock.web.entity.plan.enumtype.PlanStatus;
import com.trablock.web.repository.member.MemberRepository;
import com.trablock.web.repository.plan.ConceptRepository;
import com.trablock.web.repository.plan.PlanRepository;
import com.trablock.web.service.plan.interfaceC.ConceptService;
import com.trablock.web.service.plan.interfaceC.PlanService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ConceptServiceImplTest {

    @Autowired ConceptService conceptService;
    @Autowired ConceptRepository conceptRepository;
    @Autowired PlanService planService;
    @Autowired MemberRepository memberRepository;
    @Autowired PlanRepository planRepository;

    Plan testPlan1;
    Plan testPlan2;
    Plan testPlan3;
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

        testPlan1 = planRepository.save(plan1);
        testPlan2 = planRepository.save(plan2);
        testPlan3 = planRepository.save(plan3);
    }


    @Test
    @DisplayName("concept 생성 test")
    public void createConceptTest() throws Exception {
        //given
        List<String> conceptList = new ArrayList<>();

        conceptList.add("우정");
        conceptList.add("연인");
        conceptList.add("가족");
        conceptList.add("식도락");

        Form form = Form.builder()
                .conceptForm(
                        ConceptForm.builder()
                                .concept(conceptList)
                                .build()
                )
                .build();

        //when
        List<Concept> concept = conceptService.createConcept(form, testPlan1.getId());

        //then
        assertThat(concept.get(0).getConceptName()).isEqualTo(conceptList.get(0));
        assertThat(concept.get(1).getConceptName()).isEqualTo(conceptList.get(1));
        assertThat(concept.get(2).getConceptName()).isEqualTo(conceptList.get(2));
        assertThat(concept.get(3).getConceptName()).isEqualTo(conceptList.get(3));
    }

    @Test
    @DisplayName("concept 수정 test")
    public void updateConceptTest() throws Exception {
        //given
        List<String> conceptList = new ArrayList<>();

        conceptList.add("우정");
        conceptList.add("연인");
        conceptList.add("가족");
        conceptList.add("식도락");

        Form form = Form.builder()
                .conceptForm(
                        ConceptForm.builder()
                                .concept(conceptList)
                                .build()
                )
                .build();
        conceptService.createConcept(form, testPlan1.getId());

        //when
        List<String> updateConceptList = new ArrayList<>();

        updateConceptList.add("우정-update");
        updateConceptList.add("연인-update");
        updateConceptList.add("가족-update");
        updateConceptList.add("식도락-update");

        Form updateForm = Form.builder()
                .conceptForm(
                        ConceptForm.builder()
                                .concept(updateConceptList)
                                .build()
                )
                .build();

        List<Concept> updateConcept = conceptService.updateConcept(testPlan2.getId(), updateForm);

        //then
        assertThat(updateConcept.get(0).getConceptName()).isEqualTo(updateConceptList.get(0));
        assertThat(updateConcept.get(1).getConceptName()).isEqualTo(updateConceptList.get(1));
        assertThat(updateConcept.get(2).getConceptName()).isEqualTo(updateConceptList.get(2));
        assertThat(updateConcept.get(3).getConceptName()).isEqualTo(updateConceptList.get(3));
    }


    @Test
    @DisplayName("concept 삭제 test")
    public void removeConceptTest() throws Exception {
        //given
        List<String> conceptList = new ArrayList<>();

        conceptList.add("우정");
        conceptList.add("연인");
        conceptList.add("가족");
        conceptList.add("식도락");

        Form form = Form.builder()
                .conceptForm(
                        ConceptForm.builder()
                                .concept(conceptList)
                                .build()
                )
                .build();

        conceptService.createConcept(form, testPlan1.getId());

        //when
        conceptService.removeConcept(testPlan1);

        long deleteCount = conceptRepository.count();

        //then
        System.out.println("deleteCount = " + deleteCount);
        assertThat(deleteCount).isEqualTo(0);
    }

    @Test
    @DisplayName("planId를 통해 concept을 List<String> type으로 받아오는지 test")
    public void findConceptIdForPlanIdToListTest() throws Exception {
        //given
        List<String> conceptList = new ArrayList<>();

        conceptList.add("우정");
        conceptList.add("연인");
        conceptList.add("가족");
        conceptList.add("식도락");

        Form form = Form.builder()
                .conceptForm(
                        ConceptForm.builder()
                                .concept(conceptList)
                                .build()
                )
                .build();

        List<Concept> concept = conceptService.createConcept(form, testPlan1.getId());

        //when
        List<String> conceptNames = conceptService.findConceptIdForPlanIdToList(testPlan1.getId());

        //then
        assertThat(concept.get(0).getConceptName()).isEqualTo(conceptNames.get(0));
        assertThat(concept.get(1).getConceptName()).isEqualTo(conceptNames.get(1));
        assertThat(concept.get(2).getConceptName()).isEqualTo(conceptNames.get(2));
        assertThat(concept.get(3).getConceptName()).isEqualTo(conceptNames.get(3));
    }
}
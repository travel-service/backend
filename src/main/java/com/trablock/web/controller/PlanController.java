package com.trablock.web.controller;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.plan.*;
import com.trablock.web.repository.MemberRepository;
import com.trablock.web.service.plan.ConceptServiceImpl;
import com.trablock.web.service.plan.DayServiceImpl;
import com.trablock.web.service.plan.PlanServiceImpl;
import com.trablock.web.service.plan.SelectedLocationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlanController {

    private final PlanServiceImpl planServiceImpl;
    private final MemberRepository memberRepository;
    private final DayServiceImpl dayServiceImpl;
    private final SelectedLocationServiceImpl selectedLocationServiceImpl;
    private final ConceptServiceImpl conceptServiceImpl;

    @PostMapping("/members/{memberId}/plan")
    public String createPlan(@PathVariable("memberId") Long memberId, @RequestBody Form form) {

        Plan plan = planServiceImpl.createPlan(form, memberRepository.findMemberId(memberId));

        conceptServiceImpl.createConcept(form, memberRepository.findMemberId(memberId), plan);

        selectedLocationServiceImpl.createSelectedLocation(form, memberRepository.findMemberId(memberId), plan);

        dayServiceImpl.createDay(form, plan);

        return "redirect:/";
    }
}
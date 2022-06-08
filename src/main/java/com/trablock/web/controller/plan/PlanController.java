package com.trablock.web.controller.plan;

import com.trablock.web.controller.form.Form;
import com.trablock.web.dto.plan.*;
import com.trablock.web.entity.plan.*;
import com.trablock.web.service.plan.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planServiceImpl;
    private final DayService dayServiceImpl;
    private final SelectedLocationService selectedLocationServiceImpl;
    private final ConceptService conceptServiceImpl;

    //Plan 생성
    @PostMapping("/members/plan")
    public String createPlan(@RequestBody Form form, HttpServletRequest request) {
        Plan plan = planServiceImpl.createPlan(form, request);

        return "redirect:/";
    }

    //Concept 생성
    @PostMapping("/members/plan-concept/{planId}")
    public String createConcept(@RequestBody Form form, HttpServletRequest request, @PathVariable Long planId) {
        conceptServiceImpl.createConcept(form, request, planId);

        return "redirect:/";
    }

    //SelectedLocation 생성
    @PostMapping("/members/plan-selected-location/{planId}")
    public String createSelectedLocation(@RequestBody Form form, HttpServletRequest request, @PathVariable Long planId) {
        selectedLocationServiceImpl.createSelectedLocation(form, request, planId);

        return "redirect:/";

    }

    //Day 생성
    @PostMapping("/members/plan-day/{planId}")
    public String createDay(@RequestBody Form form, HttpServletRequest request, @PathVariable Long planId) {
        dayServiceImpl.createDay(form, request,planId);

        return "redirect:/";
    }

    //plan 정보 불러오기 - PlanForm
    @GetMapping("/user-plan/{planId}")
    public UserPlan usersPlans(@PathVariable("planId") Long id) {
        List<Plan> planList = planServiceImpl.findOne(id);
        List<PlanDto> collect = planList.stream()
                .map(p -> new PlanDto(p.getId(), p.getDepart(), p.getName(), p.getPeriods()))
                .collect(Collectors.toList());

        return new UserPlan(collect);
    }

    @Data
    @AllArgsConstructor
    static class UserPlan<T> {
        private T planForm;
    }

    //concept 정보 불러오기 - ConceptForm
    @GetMapping("/user-concept/{planId}")
    public ResponseEntity<?> usersConcepts(@PathVariable("planId") Plan id) {
        List<String> conceptIdForPlanIdToList = conceptServiceImpl.findConceptIdForPlanIdToList(id);
        Map<String, Object> conceptResult = new HashMap<>();
        conceptResult.put("conceptForm", conceptIdForPlanIdToList);
        System.out.println("id = " + id.getId());
        conceptResult.put("planId", id.getId());

        return ResponseEntity.ok().body(conceptResult);

    }

    //Day 정보 불러오기 - dayForm
    @GetMapping("/user-day/{planId}")
    public List<List<Day>> userDays(@PathVariable("planId") Long id) {
        return dayServiceImpl.findDayIdForPlanIdToList(id);
    }

    @Data
    @AllArgsConstructor
    static class UserDay<T> {

        private T dayForm;
    }
}
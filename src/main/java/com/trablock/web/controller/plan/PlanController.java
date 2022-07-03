package com.trablock.web.controller.plan;

import com.trablock.web.controller.form.Form;
import com.trablock.web.controller.form.PlanForm;
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

    private final PlanService planService;
    private final DayService dayService;
    private final SelectedLocationService selectedLocationService;
    private final ConceptService conceptService;

    //Plan 생성
    @PostMapping("/members/plan")
    public String createPlan(@RequestBody Form form, HttpServletRequest request) {
        Plan plan = planService.createPlan(form, request);

        return "redirect:/";
    }

    //Concept 생성
    @PostMapping("/members/plan-concept/{planId}")
    public String createConcept(@RequestBody Form form, HttpServletRequest request, @PathVariable Long planId) {
        conceptService.createConcept(form, request, planId);

        return "redirect:/";
    }

    //SelectedLocation 생성
    @PostMapping("/members/plan-selected-location/{planId}")
    public String createSelectedLocation(@RequestBody Form form, HttpServletRequest request, @PathVariable Long planId) {
        selectedLocationService.createSelectedLocation(form, request, planId);

        return "redirect:/";

    }

    //Day 생성
    @PostMapping("/members/plan-day/{planId}")
    public String createDay(@RequestBody Form form, HttpServletRequest request, @PathVariable Long planId) {
        dayService.createDay(form, request, planId);

        return "redirect:/";
    }

    //plan 정보 불러오기 - PlanForm
    @GetMapping("/user-plan/{planId}")
    public UserPlan usersPlans(@PathVariable("planId") Long id) {
        List<Plan> planList = planService.findOne(id);
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
        List<String> conceptIdForPlanIdToList = conceptService.findConceptIdForPlanIdToList(id);
        Map<String, Object> conceptResult = new HashMap<>();
        conceptResult.put("conceptForm", conceptIdForPlanIdToList);
        System.out.println("id = " + id.getId());
        conceptResult.put("planId", id.getId());

        return ResponseEntity.ok().body(conceptResult);

    }

    //Day 정보 불러오기 - dayForm
    @GetMapping("/user-day/{planId}")
    public UserDay userDays(@PathVariable("planId") Long id) {
        List<Day> dayList = dayService.findDayIdForPlanIdToList(id);

        List<DayDto> collect = dayList.stream()
                .map(d -> new DayDto(d.getLocations(), d.getCopyLocationId(), d.getMovingData(), d.getDays()))
                .collect(Collectors.toList());

        return new UserDay(collect);
    }

    @Data
    @AllArgsConstructor
    static class UserDay<T> {

        private T dayForm;
    }

    // plan update
    @PutMapping("/user/plan/update/{planId}")
    public void updateUserPlan(@PathVariable("planId") Long id, @RequestBody UserPlanUpdateDto userPlanUpdateDto) {
        planService.updateUserPlanContent(id, userPlanUpdateDto);
    }

    // concept 수정
    @PostMapping("/user/plan-concept/update/{planId}")
    public void updateUserPlanConcept(@PathVariable("planId") Long id, HttpServletRequest request, @RequestBody Form form) {
        conceptService.updateConcept(id, request, form);
    }

    // selectedLocation 수정
    @PostMapping("/user/plan-selectedLocation/update/{planId}")
    public void updateUserPlanSelectedLocation(@PathVariable("planId") Long id, HttpServletRequest request, @RequestBody Form form) {
        selectedLocationService.updateSelectedLocation(id, request, form);
    }

    // day 수정
    @PostMapping("/user/plan-day/update/{planId}")
    public void updateUserPlanDay(@PathVariable("planId") Long id, HttpServletRequest request, @RequestBody Form form) {
        dayService.updateDay(id, request, form);
    }
}
package com.trablock.web.controller.plan;

import com.trablock.web.controller.form.Form;
import com.trablock.web.converter.Converter;
import com.trablock.web.dto.plan.*;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.*;
import com.trablock.web.service.plan.interfaceC.ConceptService;
import com.trablock.web.service.plan.interfaceC.DayService;
import com.trablock.web.service.plan.interfaceC.PlanService;
import com.trablock.web.service.plan.interfaceC.SelectedLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/members/plan")
    public Long createPlan(@RequestBody Form form, HttpServletRequest request) {
        Plan plan = planService.createPlan(form, request);

        return plan.getId();
    }

    //Concept 생성
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/members/plan/{planId}/concept")
    public void createConcept(@RequestBody Form form, HttpServletRequest request, @PathVariable Long planId) {
        conceptService.createConcept(form, request, planId);
    }

    //SelectedLocation 생성
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/members/plan/{planId}/selected-location")
    public void createSelectedLocation(@RequestBody Form form, HttpServletRequest request, @PathVariable Long planId) {
        selectedLocationService.createSelectedLocation(form, request, planId);
    }

    //Day 생성
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/members/plan/{planId}/day")
    public void createDay(@RequestBody Form form, HttpServletRequest request, @PathVariable Long planId) {
        dayService.createDay(form, request, planId);
    }

    //plan 정보 불러오기 - PlanForm
    @GetMapping("/members/plan/{planId}")
    public Converter.UserPlan usersPlans(@PathVariable("planId") Long planId, HttpServletRequest request) {
        Member memberId = planService.findMemberId(request);
        List<Plan> planList = planService.findOne(planId, memberId);
        List<PlanDto> collect = planList.stream()
                .map(p -> new PlanDto(p.getId(), p.getDepart(), p.getName(), p.getPeriods()))
                .collect(Collectors.toList());

        return new Converter.UserPlan(collect);
    }

    // TODO 토큰 검증 방법 구현
    // concept 정보 불러오기 - ConceptForm
    @GetMapping("/members/plan/{planId}/concept")
    public ResponseEntity<?> usersConcepts(@PathVariable("planId") Long planId) {
        List<String> conceptIdForPlanIdToList = conceptService.findConceptIdForPlanIdToList(planId);
        Map<String, Object> conceptResult = new HashMap<>();
        conceptResult.put("conceptForm", conceptIdForPlanIdToList);
        conceptResult.put("planId", planId);

        return ResponseEntity.ok().body(conceptResult);
    }

    // selectedLocation 정보 불러오기
    @GetMapping("/members/plan/{planId}/selectedLocation")
    public void usersSelectedLocation(@PathVariable("planId") Long id, HttpServletRequest request) {
        Plan plan = planService.returnPlan(id, request); // 토큰 검증과 PathVariable id를 통해 Plan 객체 반환

        List<Long> locationIds = selectedLocationService.findLocationId(plan); // LocationId 리스트 형태로 반환

    }

    // TODO 토큰 검증 방법 구현
    //Day 정보 불러오기 - dayForm
    @GetMapping("/members/plan/{planId}/day")
    public Converter.UserDay userDays(@PathVariable("planId") Long planId) {
        List<Day> dayList = dayService.findDayIdForPlanIdToList(planId);

        List<DayDto> collect = dayList.stream()
                .map(d -> new DayDto(d.getCopyLocationId(), d.getMovingData(), d.getDays()))
                .collect(Collectors.toList());

        return new Converter.UserDay(collect);
    }

    // plan update
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/members/plan/{planId})")
    public void updateUserPlan(@PathVariable("planId") Long planId, HttpServletRequest request, @RequestBody UserPlanUpdateDto userPlanUpdateDto) {
        planService.updateUserPlanContent(planId, request, userPlanUpdateDto);
    }

    // concept 수정
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/members/plan/{planId}/concept")
    public void updateUserPlanConcept(@PathVariable("planId") Long planId, HttpServletRequest request, @RequestBody Form form) {
        conceptService.updateConcept(planId, request, form);
    }

    // selectedLocation 수정
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/members/plan/{planId}/selected-location")
    public void updateUserPlanSelectedLocation(@PathVariable("planId") Long planId, HttpServletRequest request, @RequestBody Form form) {
        selectedLocationService.updateSelectedLocation(planId, request, form);
    }

    // day 수정
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/members/plan/{planId}/day")
    public void updateUserPlanDay(@PathVariable("planId") Long planId, HttpServletRequest request, @RequestBody Form form) {
        dayService.updateDay(planId, request, form);
    }
}
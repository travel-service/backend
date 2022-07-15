package com.trablock.web.controller.plan;

import com.trablock.web.controller.form.Form;
import com.trablock.web.dto.plan.DayDto;
import com.trablock.web.dto.plan.PlanDto;
import com.trablock.web.dto.plan.UserPlanUpdateDto;
import com.trablock.web.entity.plan.Day;
import com.trablock.web.entity.plan.Plan;
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

import static com.trablock.web.converter.Converter.*;

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
        return planService.createPlan(form, request).getId();
    }

    //plan 정보 불러오기 - PlanForm
    @GetMapping("/members/plan/{planId}")
    public UserPlan getUserPlans(@PathVariable("planId") Long planId, HttpServletRequest request) {
        PlanDto planDto = planService.getOnePlanDto(planId, planService.getMemberFromPayload(request));
        return new UserPlan(planDto);
    }

    // concept 업데이트
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/members/plan/{planId}/concept")
    public void updateUserPlanConcept(@PathVariable("planId") Long planId, HttpServletRequest request, @RequestBody Form form) {
        conceptService.updateConcept(planId, request, form);
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

    //Day 생성
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/members/plan/{planId}/day")
    public void createDay(@RequestBody Form form, HttpServletRequest request, @PathVariable Long planId) {
        dayService.createDay(form, request, planId);
    }

    // TODO 토큰 검증 방법 구현
    //Day 정보 불러오기 - dayForm
    @GetMapping("/members/plan/{planId}/day")
    public UserDay getDaysInPlan(@PathVariable("planId") Long planId) {
        List<Day> dayList = dayService.findDayIdForPlanIdToList(planId);

        List<DayDto> collect = dayList.stream()
                .map(d -> new DayDto(d.getCopyLocationId(), d.getMovingData(), d.getDays()))
                .collect(Collectors.toList());

        return new UserDay(collect);
    }

    // day 수정
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/members/plan/{planId}/day")
    public void updateUserPlanDay(@PathVariable("planId") Long planId, HttpServletRequest request, @RequestBody Form form) {
        dayService.updateDay(planId, request, form);
    }

    // selectedLocation 정보 불러오기
    @GetMapping("/members/plan/{planId}/selected-location")
    public void usersSelectedLocation(@PathVariable("planId") Long planId, HttpServletRequest request) {
        Plan plan = planService.returnPlan(planId, request); // 토큰 검증과 PathVariable id를 통해 Plan 객체 반환
        List<Long> locationIdList = selectedLocationService.findLocationIdList(plan); // LocationId 리스트 형태로 반환
    }

    // plan update
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/members/plan/{planId})")
    public void updateUserPlan(@PathVariable("planId") Long planId, HttpServletRequest request, @RequestBody UserPlanUpdateDto userPlanUpdateDto) {
        planService.updateUserPlanContent(planId, request, userPlanUpdateDto);
    }

    // selectedLocation 수정
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/members/plan/{planId}/selected-location")
    public void updateUserPlanSelectedLocation(@PathVariable("planId") Long planId, HttpServletRequest request, @RequestBody Form form) {
        selectedLocationService.updateSelectedLocation(planId, request, form);
    }

}
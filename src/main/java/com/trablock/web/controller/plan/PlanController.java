package com.trablock.web.controller.plan;

import com.trablock.web.controller.form.Form;
import com.trablock.web.dto.plan.DayDto;
import com.trablock.web.dto.plan.PlanDto;
import com.trablock.web.dto.plan.UserPlanUpdateDto;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.Day;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.service.location.LocationService;
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

import static com.trablock.web.converter.Converter.UserDay;
import static com.trablock.web.converter.Converter.UserPlan;

@RestController
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;
    private final DayService dayService;
    private final SelectedLocationService selectedLocationService;
    private final LocationService locationService;
    private final ConceptService conceptService;

    //Plan 생성
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/members/plan")
    public Long createPlan(@RequestBody Form form, HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);

        return planService.createPlan(form, member).getId();
    }


    //plan 정보 불러오기 - PlanForm
    // TODO TEST
    @GetMapping("/members/plan/{planId}")
    public UserPlan getUserPlans(@PathVariable("planId") Long planId, HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);

        PlanDto planDto = planService.getOnePlanDto(planId, member);
        return new UserPlan(planDto);
    }


    // concept 업데이트
    // TODO TEST
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/members/plan/{planId}/concept")
    public void updateUserPlanConcept(@PathVariable("planId") Long planId,
                                      HttpServletRequest request,
                                      @RequestBody Form form) {
        conceptService.updateConcept(planId, request, form);
    }


    // concept 정보 불러오기 - ConceptForm
    @GetMapping("/members/plan/{planId}/concept")
    // TODO TEST
    public ResponseEntity<?> usersConcepts(@PathVariable("planId") Long planId, HttpServletRequest request) {
        Member memberFromPayload = planService.getMemberFromPayload(request);

        if (memberFromPayload.getId() != null) {
            List<String> conceptIdForPlanIdToList = conceptService.findConceptIdForPlanIdToList(planId);
            Map<String, Object> conceptResult = new HashMap<>();
            conceptResult.put("conceptForm", conceptIdForPlanIdToList);
            conceptResult.put("planId", planId);

            return ResponseEntity.ok().body(conceptResult);
        } else {
            throw new IllegalStateException("가입되지 않은 회원입니다.");
        }
    }


    //Day 생성
    // TODO TEST
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/members/plan/{planId}/day")
    public void createDay(@RequestBody Form form, HttpServletRequest request, @PathVariable("planId") Long planId) {
        dayService.createDay(form, request, planId);
    }


    //Day 정보 불러오기 - dayForm
    // TODO TEST
    @GetMapping("/members/plan/{planId}/day")
    public UserDay getDaysInPlan(@PathVariable("planId") Long planId, HttpServletRequest request) {
        Member memberFromPayload = planService.getMemberFromPayload(request);

        if (memberFromPayload.getId() != null) {
            List<Day> dayList = dayService.findDayIdForPlanIdToList(planId);
            List<DayDto> dayDtos = dayList.stream().map(Day::toDto).collect(Collectors.toList());
            return new UserDay(dayDtos);
        } else {
            throw new IllegalStateException("가입되지 않은 회원입니다.");
        }
    }


    // Day 수정
    // TODO TEST
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/members/plan/{planId}/day")
    public void updateUserPlanDay(@PathVariable("planId") Long planId,
                                  HttpServletRequest request,
                                  @RequestBody Form form) {
        dayService.updateDay(planId, request, form);
    }


    // SelectedLocation 정보 불러오기
    // TODO TEST
    @GetMapping("/members/plan/{planId}/selected-location")
    public ResponseEntity usersSelectedLocation(@PathVariable("planId") Long planId, HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);
        Plan plan = planService.returnPlan(planId, member); // 토큰 검증과 PathVariable id를 통해 Plan 객체 반환
        List<Long> locationIdList = selectedLocationService.findLocationIdList(plan); // LocationId 리스트 형태로 반환
        return ResponseEntity.ok().body(locationService.getMarkAndBlockLocationsFromLocationIds(locationIdList));
    }


    // Plan update
    // TODO TEST
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/members/plan/{planId}")
    public void updateUserPlan(@PathVariable("planId") Long planId,
                               HttpServletRequest request,
                               @RequestBody UserPlanUpdateDto userPlanUpdateDto) {

        Member member = planService.getMemberFromPayload(request);

        planService.updateUserPlanContent(planId, member, userPlanUpdateDto);
    }


    // SelectedLocation 수정
    // TODO TEST
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/members/plan/{planId}/selected-location")
    public void updateUserPlanSelectedLocation(@PathVariable("planId") Long planId,
                                               HttpServletRequest request,
                                               @RequestBody Form form) {
        selectedLocationService.updateSelectedLocation(planId, request, form);
    }
}
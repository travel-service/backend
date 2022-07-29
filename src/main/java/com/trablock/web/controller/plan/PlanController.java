package com.trablock.web.controller.plan;

import com.trablock.web.controller.form.Form;
import com.trablock.web.converter.Converter;
import com.trablock.web.dto.plan.DayDto;
import com.trablock.web.dto.plan.PlanDto;
import com.trablock.web.dto.plan.UserPlanUpdateDto;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.Day;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.global.HTTPStatus;
import com.trablock.web.service.location.LocationService;
import com.trablock.web.service.plan.interfaceC.ConceptService;
import com.trablock.web.service.plan.interfaceC.DayService;
import com.trablock.web.service.plan.interfaceC.PlanService;
import com.trablock.web.service.plan.interfaceC.SelectedLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.trablock.web.converter.Converter.*;
import static com.trablock.web.converter.Converter.UserDay;
import static com.trablock.web.converter.Converter.UserPlan;
import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;
    private final DayService dayService;
    private final SelectedLocationService selectedLocationService;
    private final LocationService locationService;
    private final ConceptService conceptService;

    //Plan 생성
    @PostMapping("/members/plan")
    public CreatePlan createPlan(@RequestBody Form form, HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);

        Long planId = planService.createPlan(form, member).getId();

        String message = "Plan이 정상적으로 생성되었습니다.";

        return new CreatePlan(HTTPStatus.Created.getCode(), message, planId);
    }


    //plan 정보 불러오기 - PlanForm
    // TODO TEST
    @GetMapping("/members/plan/{planId}")
    public UserPlan getUserPlans(@PathVariable("planId") Long planId, HttpServletRequest request) {
        Member member = planService.getMemberFromPayload(request);

        PlanDto planDto = planService.getOnePlanDto(planId, member);

        String message = "Plan이 정상적으로 불러와졌습니다.";

        return new UserPlan(HTTPStatus.Created.getCode(), message, planDto);
    }


    // concept 업데이트
    // TODO TEST
    @PostMapping("/members/plan/{planId}/concept")
    public UpdateConcept updateUserPlanConcept(@PathVariable("planId") Long planId,
                                      HttpServletRequest request,
                                      @RequestBody Form form) {

        Member member = planService.getMemberFromPayload(request);

        if (member.getId() == null) {
            String errorMessage = "등록되지 않은 회원입니다.";

            return new UpdateConcept(HTTPStatus.Unauthorized.getCode(), errorMessage);

        } else {
            conceptService.updateConcept(planId, form);

            String message = "Concept이 정상적으로 생성 및 업데이트 되었습니다.";

            return new UpdateConcept(HTTPStatus.Created.getCode(), message);
        }
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
    @PostMapping("/members/plan/{planId}/day")
    public CreateDay createDay(@RequestBody Form form, HttpServletRequest request, @PathVariable("planId") Long planId) {

        Member member = planService.getMemberFromPayload(request);

        if (member.getId() == null) {
            String errorMessage = "등록되지 않은 회원입니다.";

            return new CreateDay(HTTPStatus.Unauthorized.getCode(), errorMessage);
        } else {
            dayService.createDay(form, planId);

            String message = "Day가 정상적으로 생성되었습니다.";

            return new CreateDay(HTTPStatus.Created.getCode(), message);
        }
    }


    //Day 정보 불러오기 - dayForm
    // TODO TEST
    @GetMapping("/members/plan/{planId}/day")
    public UserDay getDaysInPlan(@PathVariable("planId") Long planId, HttpServletRequest request) {
        Member memberFromPayload = planService.getMemberFromPayload(request);

        String message = "Day가 정상적으로 불러와졌습니다.";

        if (memberFromPayload.getId() != null) {
            List<Day> dayList = dayService.findDayIdForPlanIdToList(planId);
            List<DayDto> dayDtos = dayList.stream().map(Day::toDto).collect(toList());
            return new UserDay(HTTPStatus.OK.getCode(), message, dayDtos);
        } else {
            String errorMessage = "등록되지 않은 회원입니다.";

            return new UserDay(HTTPStatus.Unauthorized.getCode(), errorMessage, null);
        }
    }


    // Day 수정
    // TODO TEST
    @PutMapping("/members/plan/{planId}/day")
    public GetDay updateUserPlanDay(@PathVariable("planId") Long planId,
                                  HttpServletRequest request,
                                  @RequestBody Form form) {

        Member member = planService.getMemberFromPayload(request);

        if (member.getId() == null) {
            String errorMessage = "등록되지 않은 회원입니다.";

            return new GetDay(HTTPStatus.Unauthorized.getCode(), errorMessage);
        } else {
            dayService.updateDay(planId, form);

            String message = "Day가 정상적으로 수정 되었습니다.";

            return new GetDay(HTTPStatus.Created.getCode(), message);
        }
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
    @PostMapping("/members/plan/{planId}")
    public UpdatePlan updateUserPlan(@PathVariable("planId") Long planId,
                               HttpServletRequest request,
                               @RequestBody UserPlanUpdateDto userPlanUpdateDto) {

        Member member = planService.getMemberFromPayload(request);

        planService.updateUserPlanContent(planId, member, userPlanUpdateDto);

        String message = "Plan이 정상적으로 수정 되었습니다.";

        return new UpdatePlan(HTTPStatus.Created.getCode(), message);
    }


    // SelectedLocation 수정
    // TODO TEST
    @PostMapping("/members/plan/{planId}/selected-location")
    public UpdateSelectedLocation updateUserPlanSelectedLocation(@PathVariable("planId") Long planId,
                                               HttpServletRequest request,
                                               @RequestBody Form form) {
        Member member = planService.getMemberFromPayload(request);

        if (member.getId() == null) {
            String errorMessage = "등록되지 않은 회원입니다.";

            return new UpdateSelectedLocation(HTTPStatus.Unauthorized.getCode(), errorMessage);
        } else {
            selectedLocationService.updateSelectedLocation(planId, form);

            String message = "SelectedLocation이 정상적으로 생성 및 업데이트 되었습니다.";

            return new UpdateSelectedLocation(HTTPStatus.Created.getCode(), message);
        }
    }
}
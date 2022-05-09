package com.trablock.web.controller;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.*;
import com.trablock.web.repository.MemberRepository;
import com.trablock.web.repository.location.LocationRepository;
import com.trablock.web.service.ConceptService;
import com.trablock.web.service.DayService;
import com.trablock.web.service.PlanService;
import com.trablock.web.service.SelectedLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;
    private final MemberRepository memberRepository;
    private final DayService dayService;
    private final SelectedLocationService selectedLocationService;
    private final LocationRepository locationRepository;
    private final ConceptService conceptService;

    @PostMapping("/members/{memberId}/plan")
    public String createPlan(@PathVariable("memberId") Long memberId, @RequestBody Form form) {

        Plan plan = savePlan(form, memberRepository.findMemberId(memberId));

        saveConcept(form, memberRepository.findMemberId(memberId), plan);

        saveSelectedLocation(form, memberRepository.findMemberId(memberId), plan);

        saveDay(form, plan);

        return "redirect:/";
    }

    private void saveDay(Form form, Plan plan) {
        for (int i = 0; i < form.getDayForm().getLocations().size(); i++) {
            Location locationId = locationRepository.findLocationId(form.getDayForm().getLocations().get(i).getId());

            Day day = Day.builder()
                    .locations(locationId)
                    .copyLocationId(form.getDayForm().getLocations().get(i).getCopyLocationId())
                    .days(form.getDayForm().getLocations().get(i).getDays())
                    .plan(plan)
                    .stayTime(form.getDayForm().getLocations().get(i).getStayTime())
                    .startTime(form.getDayForm().getLocations().get(i).getStartTime())
                    .vehicle(form.getDayForm().getLocations().get(i).getVehicle())
                    .movingTime(form.getDayForm().getLocations().get(i).getMovingTime())
                    .build();

            dayService.saveDay(day);
        }
    }

    private void saveSelectedLocation(Form form, Member findMemberId, Plan plan) {
        for (int i = 0; i < form.getSelectedLocationForm().getSelectedLocation().size(); i++) {
            Location locationId = locationRepository.findLocationId(form.getSelectedLocationForm().getSelectedLocation().get(i));
            SelectedLocation selectedLocation = SelectedLocation.builder()
                    .member(findMemberId)
                    .plan(plan)
                    .location(locationId)
                    .build();

            selectedLocationService.saveSelectedLocation(selectedLocation);
        }
    }

    private void saveConcept(Form form, Member findMemberId, Plan plan) {
        for (int i = 0; i < form.getConceptForm().getConcept().size(); i++) {
            Concept concept = Concept.builder()
                    .plan(plan)
                    .member(findMemberId)
                    .conceptName(form.getConceptForm().getConcept().get(i))
                    .build();

            conceptService.saveConcept(concept);
        }
    }

    private Plan savePlan(Form form, Member findMemberId) {
        Plan plan = Plan.builder()
                .depart(form.getPlanForm().getDepart())
                .destination(form.getPlanForm().getDestination())
                .member(findMemberId)
                .name(form.getPlanForm().getName())
                .periods(form.getPlanForm().getPeriods())
                .planStatus(form.getPlanForm().getPlanStatus())
                .thumbnail(form.getPlanForm().getThumbnail())
                .build();

        planService.savePlan(plan);
        return plan;
    }
}

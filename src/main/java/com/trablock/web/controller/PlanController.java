package com.trablock.web.controller;

import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.*;
import com.trablock.web.repository.DayRepository;
import com.trablock.web.repository.LocationRepository;
import com.trablock.web.repository.MemberRepository;
import com.trablock.web.repository.SelectedLocationRepository;
import com.trablock.web.service.ConceptService;
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
    private final DayRepository dayRepository;
    private final SelectedLocationService selectedLocationService;
    private final LocationRepository locationRepository;
    private final ConceptService conceptService;

    @PostMapping("/members/{memberId}/plan")
    public String createPlan(@PathVariable("memberId") Long memberId, @RequestBody PlanForm planForm) {

        Member findMemberId = memberRepository.findMemberId(memberId);

        System.out.println(planForm.getPeriods());
        Plan plan = Plan.builder()
                .depart(planForm.getDepart())
                .destination(planForm.getDestination())
                .member(findMemberId)
                .name(planForm.getName())
                .periods(planForm.getPeriods())
                .planStatus(planForm.getPlanStatus())
                .thumbnail(planForm.getThumbnail())
                .build();

        planService.savePlan(plan);

        for (int i = 0; i < planForm.getConcept().size(); i++) {
            Concept concept = Concept.builder()
                    .plan(plan)
                    .member(findMemberId)
                    .conceptName(planForm.getConcept().get(i))
                    .build();

            conceptService.saveConcept(concept);
        }


        for (int i = 0; i < planForm.getSelectedLocation().size(); i++) {
            Location locationId = locationRepository.findLocationId(planForm.getSelectedLocation().get(i));
            SelectedLocation selectedLocation = SelectedLocation.builder()
                    .member(findMemberId)
                    //.selectStatus(SelectStatus.UNSELECTED)
                    .plan(plan)
                    .location(locationId)
                    .build();

            selectedLocationService.saveSelectedLocation(selectedLocation);
        }

//        for (int i = 1; i <= planForm.getPeriods(); i++) {
//            locationRepository.findLocationId(planForm.g)
//            Day day = Day.builder()
//                    .days()
//                    .plan()
//                    .movingData()
//                    .sequence()
//                    .build();
//        }

        return "redirect:/";
    }
}

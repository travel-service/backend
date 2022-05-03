package com.trablock.web.controller;

import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.SelectStatus;
import com.trablock.web.entity.plan.SelectedLocation;
import com.trablock.web.repository.DayRepository;
import com.trablock.web.repository.LocationRepository;
import com.trablock.web.repository.MemberRepository;
import com.trablock.web.repository.SelectedLocationRepository;
import com.trablock.web.service.PlanService;
import com.trablock.web.service.SelectedLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;
    private final MemberRepository memberRepository;
    private final DayRepository dayRepository;
    private final SelectedLocationService selectedLocationService;
    private final LocationRepository locationRepository;

    @PostMapping("/members/{memberId}/plan")
    public String createPlan(@PathVariable("memberId") Long memberId,
                             @RequestBody PlanForm planForm,
                             @RequestBody SelectedLocationForm selectedLocationForm) {

        Member findMemberId = memberRepository.findMemberId(memberId);

        System.out.println(planForm.getPeriods());
        Plan plan = Plan.builder()
                .concept(planForm.getConcept())
                .depart(planForm.getDepart())
                .destination(planForm.getDestination())
                .member(findMemberId)
                .name(planForm.getName())
                .periods(planForm.getPeriods())
                .planStatus(planForm.getPlanStatus())
                .thumbnail(planForm.getThumbnail())
                .build();

        for (int i = 0; i < selectedLocationForm.getSelectedLocation().size(); i++) {
            Location locationId = locationRepository.findLocationId(selectedLocationForm.getSelectedLocation().get(i));
            SelectedLocation selectedLocation = SelectedLocation.builder()
                    .member(findMemberId)
                    .selectStatus(SelectStatus.UNSELECTED)
                    .plan(plan)
                    .location(locationId)
                    .build();

            selectedLocationService.saveSelectedLocation(selectedLocation);
        }

        planService.savePlan(plan);
        return "redirect:/";
    }
}

package com.trablock.web.service.plan;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.SelectedLocation;
import com.trablock.web.repository.plan.SelectedLocationRepository;
import com.trablock.web.service.plan.planInterface.PlanService;
import com.trablock.web.service.plan.planInterface.SelectedLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SelectedLocationServiceImpl implements SelectedLocationService {

    private final SelectedLocationRepository selectedLocationRepository;
    private final PlanService planService;

    @Transactional
    public void saveSelectedLocation(SelectedLocation selectedLocation) {
        selectedLocationRepository.save(selectedLocation);
    }

    @Override
    @Transactional
    public void createSelectedLocation(Form form, HttpServletRequest request, Plan plan) {
        for (int i = 0; i < form.getSelectedLocationForm().getSelectedLocation().size(); i++) {
            //Location locationId = locationRepository.findLocationId(form.getSelectedLocationForm().getSelectedLocation().get(i));
            SelectedLocation selectedLocation = SelectedLocation.builder()
//                    .member(planService.findMemberId(request))
                    .plan(plan)
                    //.location(locationId)
                    .build();

            saveSelectedLocation(selectedLocation);
        }
    }


}

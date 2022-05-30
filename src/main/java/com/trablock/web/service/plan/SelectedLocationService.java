package com.trablock.web.service.plan;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.SelectedLocation;
import com.trablock.web.repository.plan.PlanRepository;
import com.trablock.web.repository.plan.SelectedLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SelectedLocationService {

    private final SelectedLocationRepository selectedLocationRepository;
    private final PlanRepository planRepository;

    @Transactional
    public void saveSelectedLocation(SelectedLocation selectedLocation) {
        selectedLocationRepository.save(selectedLocation);
    }

    @Transactional
    public void createSelectedLocation(Form form, HttpServletRequest request, Long plan) {
        Plan planById = planRepository.findPlanById(plan);

        for (int i = 0; i < form.getSelectedLocationForm().getSelectedLocation().size(); i++) {
            //Location locationId = locationRepository.findLocationId(form.getSelectedLocationForm().getSelectedLocation().get(i));
            SelectedLocation selectedLocation = SelectedLocation.builder()
                    .plan(planById)
                    //.location(locationId)
                    .build();

            saveSelectedLocation(selectedLocation);
        }
    }


}

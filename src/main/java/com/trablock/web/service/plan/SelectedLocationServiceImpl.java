package com.trablock.web.service.plan;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.SelectedLocation;
import com.trablock.web.repository.location.LocationRepository;
import com.trablock.web.repository.plan.PlanRepository;
import com.trablock.web.repository.plan.SelectedLocationRepository;
import com.trablock.web.service.plan.interfaceC.SelectedLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SelectedLocationServiceImpl implements SelectedLocationService {

    private final SelectedLocationRepository selectedLocationRepository;
    private final PlanRepository planRepository;
    private final LocationRepository locationRepository;

    @Override
    @Transactional
    public List<SelectedLocation> createSelectedLocation(Form form, Long planId) {
        Plan plan = planRepository.findPlanById(planId).orElseThrow();

        ArrayList<SelectedLocation> selectedLocationList = new ArrayList<>();

        for (int i = 0; i < form.getSelectedLocationForm().getSelectedLocation().size(); i++) {
            Optional<Location> OptionalLocation = locationRepository.findLocationById(form.getSelectedLocationForm().getSelectedLocation().get(i));

            SelectedLocation selectedLocation = SelectedLocation.builder()
                    .plan(plan)
                    .location(OptionalLocation.orElseThrow())
                    .build();

            selectedLocationList.add(selectedLocation);
        }

        return selectedLocationRepository.saveAll(selectedLocationList);
    }

    /**
     * SelectedLocation Update
     *  @param planId
     * @param form
     * @return
     */
    @Override
    @Transactional
    public List<SelectedLocation> updateSelectedLocation(Long planId, Form form) {
        Plan plan = planRepository.findPlanById(planId).orElseThrow();
        removeSelectedLocation(plan);
        return createSelectedLocation(form, planId);
    }

    /**
     * SelectedLocation Delete
     *
     * @param plan
     */
    @Override
    @Transactional
    public void removeSelectedLocation(Plan plan) {
        List<SelectedLocation> selectedLocations = selectedLocationRepository.findSelectedLocationByPlanId(plan);
        if (selectedLocations == null || selectedLocations.isEmpty()) {
            return;
        }
        selectedLocations.forEach(selectedLocationRepository::delete);
    }

    /**
     * Location ID 반환
     *
     * @param plan
     * @return
     */
    @Override
    public List<Long> findLocationIdList(Plan plan) {
        return selectedLocationRepository.findLocationIdByPlanId(plan);
    }
}

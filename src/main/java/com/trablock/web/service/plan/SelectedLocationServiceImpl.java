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

import javax.servlet.http.HttpServletRequest;
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
    public void saveSelectedLocation(SelectedLocation selectedLocation) {
        selectedLocationRepository.save(selectedLocation);
    }

    @Override
    @Transactional
    public void createSelectedLocation(Form form, HttpServletRequest request, Long planId) {
        Plan plan = planRepository.findPlanById(planId).orElseThrow();

        for (int i = 0; i < form.getSelectedLocationForm().getSelectedLocation().size(); i++) {
            Optional<Location> OptionalLocation = locationRepository.findLocationById(form.getSelectedLocationForm().getSelectedLocation().get(i));
            SelectedLocation selectedLocation = SelectedLocation.builder()
                    .plan(plan)
                    .location(OptionalLocation.get())
                    .build();

            saveSelectedLocation(selectedLocation);
        }
    }

    /**
     * SelectedLocation Update
     *
     * @param planId
     * @param request
     * @param form
     */
    @Override
    @Transactional
    public void updateSelectedLocation(Long planId, HttpServletRequest request, Form form) {
        Plan plan = planRepository.findPlanById(planId).orElseThrow();
        removeSelectedLocation(plan);
        createSelectedLocation(form, request, planId);
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

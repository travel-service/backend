package com.trablock.web.service.plan;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.SelectedLocation;
import com.trablock.web.repository.location.LocationRepository;
import com.trablock.web.repository.plan.PlanRepository;
import com.trablock.web.repository.plan.SelectedLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SelectedLocationService {

    private final SelectedLocationRepository selectedLocationRepository;
    private final PlanRepository planRepository;
    private final LocationRepository locationRepository;

    @Transactional
    public void saveSelectedLocation(SelectedLocation selectedLocation) {
        selectedLocationRepository.save(selectedLocation);
    }

    @Transactional
    public void createSelectedLocation(Form form, HttpServletRequest request, Long plan) {
        Plan planById = planRepository.findPlanById(plan);

        for (int i = 0; i < form.getSelectedLocationForm().getSelectedLocation().size(); i++) {
            Optional<Location> locationById = locationRepository.findLocationById(form.getSelectedLocationForm().getSelectedLocation().get(i));
            SelectedLocation selectedLocation = SelectedLocation.builder()
                    .plan(planById)
                    .location(locationById.get())
                    .build();

            saveSelectedLocation(selectedLocation);
        }
    }

    /**
     * SelectedLocation Update
     * @param id
     * @param request
     * @param form
     */
    @Transactional
    public void updateSelectedLocation(Long id, HttpServletRequest request, Form form) {
        Plan plan = planRepository.findPlanById(id);

        removeSelectedLocation(plan);

        createSelectedLocation(form, request, plan.getId());
    }

    /**
     * SelectedLocation Delete
     * @param plan
     */
    @Transactional
    public void removeSelectedLocation(Plan plan) {
        List<SelectedLocation> selectedLocationByPlanId = selectedLocationRepository.findSelectedLocationByPlanId(plan);

        for (SelectedLocation selectedLocation : selectedLocationByPlanId) {
            selectedLocationRepository.delete(selectedLocation);
        }
    }

    /**
     * Location ID 반환
     * @param plan
     * @return
     */
    public List<Long> findLocationId(Plan plan) {
        return selectedLocationRepository.findLocationIdByPlanId(plan);
    }
}

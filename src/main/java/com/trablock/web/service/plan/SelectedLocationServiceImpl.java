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
    public void createSelectedLocation(Form form, HttpServletRequest request, Long plan) {
        Plan planById = planRepository.findPlanById(plan);

        for (int i = 0; i < form.getSelectedLocationForm().getSelectedLocation().size(); i++) {
            Optional<Location> OptionalLocation = locationRepository.findLocationById(form.getSelectedLocationForm().getSelectedLocation().get(i));
            SelectedLocation selectedLocation = SelectedLocation.builder()
                    .plan(planById)
                    .location(OptionalLocation.get())
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
    @Override
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
    @Override
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
    @Override
    public List<Long> findLocationId(Plan plan) {
        return selectedLocationRepository.findLocationIdByPlanId(plan);
    }
}

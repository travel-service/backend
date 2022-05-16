package com.trablock.web.service.plan;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.SelectedLocation;
import com.trablock.web.repository.PlanRepository;
import com.trablock.web.repository.SelectedLocationRepository;
import com.trablock.web.repository.location.LocationRepository;
import com.trablock.web.service.plan.planInterface.SelectedLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SelectedLocationServiceImpl implements SelectedLocationService {

    @PersistenceContext
    EntityManager em;
    private final SelectedLocationRepository selectedLocationRepository;
    private final LocationRepository locationRepository;

    @Transactional
    public void saveSelectedLocation(SelectedLocation selectedLocation) {
        selectedLocationRepository.save(selectedLocation);
    }

    @Override
    @Transactional
    public void createSelectedLocation(Form form, Member findMemberId, Plan plan) {
        for (int i = 0; i < form.getSelectedLocationForm().getSelectedLocation().size(); i++) {
            //Location locationId = locationRepository.findLocationId(form.getSelectedLocationForm().getSelectedLocation().get(i));
            SelectedLocation selectedLocation = SelectedLocation.builder()
                    .member(findMemberId)
                    .plan(plan)
                    //.location(locationId)
                    .build();

            saveSelectedLocation(selectedLocation);
        }
    }


}

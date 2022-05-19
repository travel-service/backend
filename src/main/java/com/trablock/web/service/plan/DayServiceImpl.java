package com.trablock.web.service.plan;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.plan.Day;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.repository.plan.DayRepository;
import com.trablock.web.repository.location.LocationRepository;
import com.trablock.web.service.plan.planInterface.DayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DayServiceImpl implements DayService {

    private final DayRepository dayRepository;
    private final LocationRepository locationRepository;

    @Transactional
    public void saveDay(Day day) {
        dayRepository.save(day);
    }

    @Override
    @Transactional
    public void createDay(Form form, Plan plan) {
        for (int i = 0; i < form.getDayForm().getTravelDay().size(); i++) {
            for (int j = 0; j < form.getDayForm().getTravelDay().get(i).size(); j++) {
               //Location locationId = locationRepository.findLocationId(form.getDayForm().getTravelDay().get(i).get(j).getId());

                Day day = Day.builder()
                        //.locations(locationId)
                        .copyLocationId(form.getDayForm().getTravelDay().get(i).get(j).getCopyLocationId())
                        .plan(plan)
                        .movingData(form.getDayForm().getTravelDay().get(i).get(j).getMovingData())
                        .build();

                saveDay(day);
            }
        }
    }
}

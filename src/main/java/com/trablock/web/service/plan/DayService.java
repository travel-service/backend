package com.trablock.web.service.plan;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.plan.Day;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.repository.plan.DayRepository;
import com.trablock.web.repository.plan.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DayService {

    private final DayRepository dayRepository;
    private final PlanRepository planRepository;
//    private final LocationRepository locationRepository;

    @Transactional
    public void saveDay(Day day) {
        dayRepository.save(day);
    }

    @Transactional
    public void createDay(Form form, HttpServletRequest request, Long plan) {
        Plan planById = planRepository.findPlanById(plan);

        for (int i = 0; i < form.getDayForm().getTravelDay().size(); i++) {
            for (int j = 0; j < form.getDayForm().getTravelDay().get(i).size(); j++) {
//                Location locationId = locationRepository.findLocationId(form.getDayForm().getLocationId());

                Day day = Day.builder()
//                        .locations(locationId)
                        .copyLocationId(form.getDayForm().getTravelDay().get(i).get(j).getCopyLocationId())
                        .plan(planById)
                        .days(form.getDayForm().getTravelDay().get(i).get(j).getDays())
                        .movingData(form.getDayForm().getTravelDay().get(i).get(j).getMovingData())
                        .build();

                saveDay(day);
            }
        }
    }

    /**
     * day List 받아오기
     * @param id
     * @return
     */
    public List<Day> findDayIdForPlanIdToList(Long id) {

        Plan plan = planRepository.findPlanById(id);

        return dayRepository.findByDayToList(plan);

    }
}

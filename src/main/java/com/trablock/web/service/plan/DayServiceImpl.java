package com.trablock.web.service.plan;

import com.trablock.web.controller.form.DayForm;
import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.plan.Day;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.repository.location.LocationRepository;
import com.trablock.web.repository.plan.DayRepository;
import com.trablock.web.repository.plan.PlanRepository;
import com.trablock.web.service.plan.interfaceC.DayService;
import com.trablock.web.service.plan.interfaceC.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DayServiceImpl implements DayService {

    private final DayRepository dayRepository;
    private final PlanRepository planRepository;
    private final PlanService planService;
    private final LocationRepository locationRepository;

    @Override
    @Transactional
    // TODO TEST
    public void saveDay(Day day) {
        dayRepository.save(day);
    }

    @Override
    @Transactional
    // TODO TEST
    public void createDay(Form form, HttpServletRequest request, Long planId) {
        Plan plan = planRepository.findPlanById(planId).orElseThrow();
        planService.finishedPlan(planId);
        DayForm dayForm = form.getDayForm();

        ArrayList<Day> dayList = new ArrayList();

        for (int i = 0; i < dayForm.getTravelDay().size(); i++) {
            for (int j = 0; j < dayForm.getTravelDay().get(i).size(); j++) {

                Optional<Location> optionalLocation = locationRepository.findLocationById(dayForm.getTravelDay().get(i).get(j).getLocationId());

                Day day = Day.builder()
                        .location(optionalLocation.orElseThrow())
                        .copyLocationId(dayForm.getTravelDay().get(i).get(j).getCopyLocationId())
                        .plan(plan)
                        .days(dayForm.getTravelDay().get(i).get(j).getDays())
                        .movingData(dayForm.getTravelDay().get(i).get(j).getMovingData())
                        .build();

                dayList.add(day);
            }
        }

        dayRepository.saveAll(dayList);
    }

    /**
     * day List 받아오기
     *
     * @param planId
     * @return
     */
    @Override
    // TODO TEST
    public List<Day> findDayIdForPlanIdToList(Long planId) {
        Plan plan = planRepository.findPlanById(planId).orElseThrow();
        return dayRepository.findDaysByPlan(plan);
    }

    /**
     * Day Update
     *
     * @param planId
     * @param request
     * @param form
     */
    @Override
    @Transactional
    // TODO TEST
    public void updateDay(Long planId, HttpServletRequest request, Form form) {
        Plan plan = planRepository.findPlanById(planId).orElseThrow();
        removeDay(plan);
        planService.unFinishedPlan(planId);
        createDay(form, request, plan.getId());
    }

    /**
     * Day Delete
     *
     * @param plan
     */
    @Override
    @Transactional
    // TODO TEST
    public void removeDay(Plan plan) {
        List<Day> dayList = dayRepository.findDaysByPlan(plan);
        if (!dayList.isEmpty())
            dayList.forEach(dayRepository::delete);
    }
}

package com.trablock.web.service.plan;

import com.trablock.web.controller.form.DayForm;
import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.plan.Day;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.enumtype.PlanComplete;
import com.trablock.web.repository.location.LocationRepository;
import com.trablock.web.repository.plan.DayRepository;
import com.trablock.web.repository.plan.PlanRepository;
import com.trablock.web.service.plan.interfaceC.DayService;
import com.trablock.web.service.plan.interfaceC.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Day> createDay(Form form, Long planId) {
        Plan plan = planRepository.findPlanById(planId).orElseThrow();

        if (plan.getPlanComplete() == PlanComplete.FINISHED) {
            planService.unFinishedPlan(planId);
        }
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

        return dayRepository.saveAll(dayList);
    }

    /**
     * day List 받아오기
     *
     * @param planId
     * @return
     */
    @Override
    public List<Day> findDayIdForPlanIdToList(Long planId) {
        Plan plan = planRepository.findPlanById(planId).orElseThrow();
        return dayRepository.findDaysByPlan(plan);
    }

    /**
     * Day Update
     *  @param planId
     * @param form
     * @return
     */
    @Override
    @Transactional
    public List<Day> updateDay(Long planId, Form form) {
        Plan plan = planRepository.findPlanById(planId).orElseThrow();
        removeDay(plan);
        planService.unFinishedPlan(planId);
        return createDay(form, plan.getId());
    }

    /**
     * Day Delete
     *
     * @param plan
     */
    @Override
    @Transactional
    public void removeDay(Plan plan) {
        List<Day> dayList = dayRepository.findDaysByPlan(plan);
        if (!dayList.isEmpty())
            dayList.forEach(dayRepository::delete);
    }
}

package com.trablock.web.service.plan.interfaceC;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.plan.Day;
import com.trablock.web.entity.plan.Plan;

import java.util.List;

public interface DayService {

    List<Day> createDay(Form form, Long plan);

    List<Day> findDayIdForPlanIdToList(Long id);

    List<Day> updateDay(Long id, Form form);

    void removeDay(Plan plan);
}

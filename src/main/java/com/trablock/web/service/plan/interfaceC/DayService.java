package com.trablock.web.service.plan.interfaceC;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.plan.Day;
import com.trablock.web.entity.plan.Plan;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface DayService {

    void createDay(Form form, Long plan);

    List<Day> findDayIdForPlanIdToList(Long id);

    void updateDay(Long id, Form form);

    void removeDay(Plan plan);
}

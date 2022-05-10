package com.trablock.web.service.plan.planInterface;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.plan.Day;
import com.trablock.web.entity.plan.Plan;

public interface DayService {

    void saveDay(Day day);

    void createDay(Form form, Plan plan);
}

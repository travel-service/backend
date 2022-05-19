package com.trablock.web.service.plan.planInterface;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.SelectedLocation;

import javax.servlet.http.HttpServletRequest;

public interface SelectedLocationService {

    void saveSelectedLocation(SelectedLocation selectedLocation);

    void createSelectedLocation(Form form, HttpServletRequest request, Plan plan);

}

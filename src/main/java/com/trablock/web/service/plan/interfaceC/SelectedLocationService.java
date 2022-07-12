package com.trablock.web.service.plan.interfaceC;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.SelectedLocation;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SelectedLocationService {

    void saveSelectedLocation(SelectedLocation selectedLocation);

    void createSelectedLocation(Form form, HttpServletRequest request, Long plan);

    void updateSelectedLocation(Long id, HttpServletRequest request, Form form);

    void removeSelectedLocation(Plan plan);

    List<Long> findLocationId(Plan plan);
}

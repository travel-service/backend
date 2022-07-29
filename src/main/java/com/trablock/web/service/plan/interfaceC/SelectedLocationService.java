package com.trablock.web.service.plan.interfaceC;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.SelectedLocation;

import java.util.List;

public interface SelectedLocationService {

    List<SelectedLocation> createSelectedLocation(Form form, Long plan);

    List<SelectedLocation> updateSelectedLocation(Long id, Form form);

    void removeSelectedLocation(Plan plan);

    List<Long> findLocationIdList(Plan plan);
}

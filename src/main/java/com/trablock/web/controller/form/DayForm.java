package com.trablock.web.controller.form;

import com.trablock.web.entity.plan.Day;
import com.trablock.web.entity.plan.MovingData;
import lombok.Getter;

import java.util.List;

@Getter
public class DayForm {

    private Long locationId;
    private List<List<Day>> travelDay;
    private MovingData movingData;
    private String copyLocationId;
}

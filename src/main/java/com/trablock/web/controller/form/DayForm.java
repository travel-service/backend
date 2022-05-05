package com.trablock.web.controller.form;

import com.trablock.web.entity.plan.Day;
import lombok.Getter;

import java.util.List;

@Getter
public class DayForm {

    private Long id;
    private List<Day> locations;
    private int sequence;
    private int days;
    private String vehicle;
    private String movingTime;
    private String stayTime;
    private String startTime;
    private String copyLocationId;
}

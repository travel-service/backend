package com.trablock.web.controller;

import com.trablock.web.entity.plan.Day;
import com.trablock.web.entity.plan.MovingData;
import com.trablock.web.entity.plan.PlanStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PlanForm {

    //plan form
    private String depart;
    private String name;
    private String destination;
    private int periods;
    private PlanStatus planStatus;
    private String thumbnail;

    //concept form
    private List<String> concept;

    //selectedLocation Form
    private List<Long> selectedLocation;

    //day form
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

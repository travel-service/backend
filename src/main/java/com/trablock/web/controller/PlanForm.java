package com.trablock.web.controller;

import com.trablock.web.entity.plan.PlanStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PlanForm {

    private String concept;
    private String depart;
    private String name;
    private String destination;
    private int periods;
    private PlanStatus planStatus;
    private String thumbnail;
}

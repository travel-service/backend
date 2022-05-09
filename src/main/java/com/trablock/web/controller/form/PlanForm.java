package com.trablock.web.controller.form;

import com.trablock.web.entity.plan.PlanStatus;
import lombok.Getter;

@Getter
public class PlanForm {

    private String depart;
    private String name;
    private String destination;
    private int periods;
    private PlanStatus planStatus;
    private String thumbnail;
}

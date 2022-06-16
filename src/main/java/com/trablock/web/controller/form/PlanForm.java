package com.trablock.web.controller.form;

import com.trablock.web.entity.plan.enumtype.PlanComplete;
import com.trablock.web.entity.plan.enumtype.PlanStatus;
import lombok.Getter;

@Getter
public class PlanForm {

    private String depart;
    private String name;
    private int periods;
    private PlanStatus planStatus;
    private String thumbnail;
    private PlanComplete planComplete;
}

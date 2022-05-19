package com.trablock.web.repository.plan;

import com.trablock.web.entity.plan.PlanStatus;
import lombok.Getter;

@Getter
public class PlanSearch {

    private String planName;
    private PlanStatus planStatus;
}

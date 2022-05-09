package com.trablock.web.repository.query;

import com.trablock.web.entity.plan.PlanStatus;
import lombok.Data;

@Data
public class PlanQueryDto {

    private Long planId;
    private String concept;
    private String depart;
    private String name;
    private int periods;
    private PlanStatus planStatus;
    private String thumbnail;

    public PlanQueryDto(Long planId, String concept, String depart, String name, int periods, PlanStatus planStatus, String thumbnail) {
        this.planId = planId;
        this.concept = concept;
        this.depart = depart;
        this.name = name;
        this.periods = periods;
        this.planStatus = planStatus;
        this.thumbnail = thumbnail;
    }
}

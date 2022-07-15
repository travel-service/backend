package com.trablock.web.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PlanDto {

    private Long planId;
    private String depart;
    private String name;
    private int periods;
}

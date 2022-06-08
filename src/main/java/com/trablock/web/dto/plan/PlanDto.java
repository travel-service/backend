package com.trablock.web.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class PlanDto {

    private Long planId;
    private String depart;
    private String name;
    private int periods;
}

package com.trablock.web.dto.plan;

import com.trablock.web.entity.plan.enumtype.PlanComplete;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PlanDirectoryDto {

    private Long planId;
    private String name;
    private int periods;
    private String createdDate;
    private PlanComplete planComplete;

}

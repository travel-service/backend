package com.trablock.web.dto.plan;

import com.trablock.web.entity.plan.Plan;
import lombok.*;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Data
@Getter
@AllArgsConstructor
public class PlanDirectoryDto {

    private Long planId;
    private String name;
    private int periods;
    private LocalDateTime createdDate;

}

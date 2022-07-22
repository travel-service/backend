package com.trablock.web.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPlanUpdateDto {

    private String depart;
    private String name;
    private int periods;
    private String thumbnail;
}

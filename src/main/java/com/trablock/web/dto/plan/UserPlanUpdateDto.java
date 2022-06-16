package com.trablock.web.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPlanUpdateDto {

    private String depart;
    private String name;
    private int periods;
    private String thumbnail;
}

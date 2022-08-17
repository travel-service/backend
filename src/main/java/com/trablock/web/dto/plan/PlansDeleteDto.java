package com.trablock.web.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
@AllArgsConstructor
public class PlansDeleteDto {

    private List<Long> planIds;
}

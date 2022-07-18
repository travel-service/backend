package com.trablock.web.dto.plan;

import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.plan.MovingData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DayDto {

    private Long locationId;
    private String copyLocationId;
    private MovingData movingData;
    private int days;

}

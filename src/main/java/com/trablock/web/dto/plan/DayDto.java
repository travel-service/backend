package com.trablock.web.dto.plan;

import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.plan.MovingData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
@AllArgsConstructor
public class DayDto {

    private Location locationId;
    private String copyLocationId;
    private MovingData movingData;
    private int days;
}

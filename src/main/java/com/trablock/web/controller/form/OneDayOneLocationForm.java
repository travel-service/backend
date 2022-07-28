package com.trablock.web.controller.form;

import com.trablock.web.entity.plan.MovingData;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class OneDayOneLocationForm {

    private Long locationId;
    private MovingData movingData;
    private String copyLocationId;
    private int days;
}

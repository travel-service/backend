package com.trablock.web.controller.form;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DayForm {
    private List<List<OneDayOneLocationForm>> travelDay;
}

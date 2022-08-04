package com.trablock.web.controller.form;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Form {

    private PlanForm planForm;
    private ConceptForm conceptForm;
    private SelectedLocationForm selectedLocationForm;
    private DayForm dayForm;
}

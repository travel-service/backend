package com.trablock.web.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserPlanConceptUpdateDto {

    private List<String> concept;
}

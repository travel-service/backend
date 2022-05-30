package com.trablock.web.dto.plan;

import com.trablock.web.entity.plan.Concept;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
@AllArgsConstructor
public class ConceptDto {

    private Long planId;
    private List<String> concept;
}

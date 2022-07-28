package com.trablock.web.repository.plan;

import com.trablock.web.dto.plan.PlanInfoDto;

import java.util.List;

public interface PlanRepositoryCustom {

    List<PlanInfoDto> findPlanInfoCustom(Long memberId);
}

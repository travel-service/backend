package com.trablock.web.repository.plan;

import com.trablock.web.dto.plan.PlanInfoDto;
import com.trablock.web.dto.plan.UserDirectoryIdDto;

import java.util.List;

public interface PlanItemRepositoryCustom {
    List<UserDirectoryIdDto> findUserDirectoryIdByPlanIdCustom(List<Long> toPlanId);
}

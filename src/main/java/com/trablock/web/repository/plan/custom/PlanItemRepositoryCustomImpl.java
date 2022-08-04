package com.trablock.web.repository.plan.custom;

import com.trablock.web.dto.plan.UserDirectoryIdDto;
import com.trablock.web.repository.plan.PlanItemRepositoryCustom;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class PlanItemRepositoryCustomImpl implements PlanItemRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<UserDirectoryIdDto> findUserDirectoryIdByPlanIdCustom(List<Long> toPlanId) {
        return em.createQuery(
                "select new com.trablock.web.dto.plan.UserDirectoryIdDto(pi.plan.id, pi.userDirectory.id)" +
                        " from PlanItem pi" +
                        " where pi.plan.id in :toPlanId", UserDirectoryIdDto.class)
                .setParameter("toPlanId", toPlanId)
                .getResultList();
    }
}

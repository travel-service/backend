package com.trablock.web.repository.plan.custom;

import com.trablock.web.dto.plan.PlanInfoDto;
import com.trablock.web.repository.plan.PlanRepositoryCustom;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class PlanRepositoryCustomImpl implements PlanRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<PlanInfoDto> findPlanInfoCustom(Long memberId) {
        return em.createQuery(
                "select new com.trablock.web.dto.plan.PlanInfoDto(p.id, p.name, p.periods, p.createdDate, p.planComplete)" +
                        " from Plan p" +
                        " left join p.planItems pi" +
                        " where p.member.id = :memberId", PlanInfoDto.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }
}

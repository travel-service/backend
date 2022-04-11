package com.trablock.web.repository.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlanQueryRepository {

    @PersistenceContext
    EntityManager em;

//    public List<PlanQueryDto> findPlanQueryDtos() {
//        List<PlanQueryDto> plans = findPlans();
//
//
//    }

    private List<PlanQueryDto> findPlans() {
        return em.createQuery(
                "select new com.trablock.web.repository.query.PlanQueryDto(p.id, p.concept, p.depart, p.name, p.periods, p.planStatus, p.thumbnail)" +
                        " from Plan p" +
                        " join p.member m", PlanQueryDto.class)
                .getResultList();
    }

}

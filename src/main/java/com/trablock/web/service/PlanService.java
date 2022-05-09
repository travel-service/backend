package com.trablock.web.service;

import com.trablock.web.entity.plan.Plan;
import com.trablock.web.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlanService {

    @PersistenceContext
    EntityManager em;
    private final PlanRepository planRepository;

    @Transactional
    public void savePlan(Plan plan) {
        planRepository.save(plan);
    }

    public Plan findOne(Long planId) {
        return planRepository.findPlanById(planId);
    }

    public List<Plan> findAll() {
        return em.createQuery("select p from Plan p", Plan.class)
                .getResultList();
    }
}

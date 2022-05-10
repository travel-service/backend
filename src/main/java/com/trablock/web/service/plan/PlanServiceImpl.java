package com.trablock.web.service.plan;

import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.repository.PlanRepository;
import com.trablock.web.service.plan.planInterface.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

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

    @Override
    @Transactional
    public Plan createPlan(Form form, Member findMemberId) {
        Plan plan = Plan.builder()
                .depart(form.getPlanForm().getDepart())
                .destination(form.getPlanForm().getDestination())
                .member(findMemberId)
                .name(form.getPlanForm().getName())
                .periods(form.getPlanForm().getPeriods())
                .planStatus(form.getPlanForm().getPlanStatus())
                .thumbnail(form.getPlanForm().getThumbnail())
                .build();

        savePlan(plan);
        return plan;
    }
}

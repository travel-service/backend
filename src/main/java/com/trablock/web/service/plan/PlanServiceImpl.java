package com.trablock.web.service.plan;

import com.trablock.web.config.jwt.JwtTokenProvider;
import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.repository.MemberRepository;
import com.trablock.web.repository.plan.PlanRepository;
import com.trablock.web.service.plan.planInterface.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;


    @Transactional
    public void savePlan(Plan plan) {
        planRepository.save(plan);
    }

    public Plan findOne(Long planId) {
        return planRepository.findPlanById(planId);
    }

    @Override
    public Member findMemberId(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveAccessToken(request);
        String userName = jwtTokenProvider.getUserName(token);
        Optional<Member> userId = memberRepository.findByUserName(userName);
        if (userId.isPresent()) {
            return userId.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    @Transactional
    public Plan createPlan(Form form, HttpServletRequest request) {

        Plan plan = Plan.builder()
                .depart(form.getPlanForm().getDepart())
                .member(findMemberId(request))
                .name(form.getPlanForm().getName())
                .periods(form.getPlanForm().getPeriods())
                .planStatus(form.getPlanForm().getPlanStatus())
                .thumbnail(form.getPlanForm().getThumbnail())
                .build();

        savePlan(plan);
        return plan;
    }

    @Override
    public List<Plan> findMainPlanDirectoryMain(HttpServletRequest request) {
        Optional<Member> memberId = Optional.ofNullable(findMemberId(request));
        return planRepository.findByMainPlanStatus(memberId);
    }

    @Override
    public List<Plan> findTrashPlanDirectoryMain(HttpServletRequest request) {
        Optional<Member> memberId = Optional.ofNullable(findMemberId(request));
        return planRepository.findByTrashPlanStatus(memberId);
    }

    /**
     * 플랜 삭제(main -> trash)
     */
    @Transactional
    @Override
    public void cancelPlan(Long planId) {
        Plan plan = planRepository.findPlanById(planId);
        plan.trash();
    }

    /**
     * 플랜 완전 삭제(trash -> delete)
     */
    @Transactional
    @Override
    public void deletePlan(Long planId) {
        Plan plan = planRepository.findPlanById(planId);
        plan.delete();
    }

    /**
     * 플랜 완전 삭제(trash -> main)
     */
    @Transactional
    @Override
    public void revertPlan(Long planId) {
        Plan plan = planRepository.findPlanById(planId);
        plan.revert();
    }

    /**
     * 이동(user -> main)
     */


    /**
     * 이동(user -> user)
     */
}

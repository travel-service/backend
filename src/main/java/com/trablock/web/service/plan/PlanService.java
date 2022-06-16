package com.trablock.web.service.plan;

import com.trablock.web.config.jwt.JwtTokenProvider;
import com.trablock.web.controller.form.Form;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.enumtype.PlanComplete;
import com.trablock.web.repository.MemberRepository;
import com.trablock.web.repository.plan.PlanRepository;
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
public class PlanService {

    private final PlanRepository planRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;


    @Transactional
    public void savePlan(Plan plan) {
        planRepository.save(plan);
    }

    public List<Plan> findOne(Long planId) {
        return planRepository.findByIdToList(planId);
    }

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

    @Transactional
    public Plan createPlan(Form form, HttpServletRequest request) {

        Plan plan = Plan.builder()
                .depart(form.getPlanForm().getDepart())
                .member(findMemberId(request))
                .name(form.getPlanForm().getName())
                .periods(form.getPlanForm().getPeriods())
                .planStatus(form.getPlanForm().getPlanStatus())
                .thumbnail(form.getPlanForm().getThumbnail())
                .planComplete(PlanComplete.UNFINISHED)
                .build();

        savePlan(plan);
        return plan;
    }

    public List<Plan> findMainPlanDirectoryMain(HttpServletRequest request) {
        Optional<Member> memberId = Optional.ofNullable(findMemberId(request));
        return planRepository.findByMainPlanStatus(memberId);
    }

    public List<Plan> findTrashPlanDirectoryMain(HttpServletRequest request) {
        Optional<Member> memberId = Optional.ofNullable(findMemberId(request));
        return planRepository.findByTrashPlanStatus(memberId);
    }


     // 플랜 삭제(main -> trash)
    @Transactional
    public void cancelPlan(Long planId) {
        Plan plan = planRepository.findPlanById(planId);
        plan.trash();
    }

    // 플랜 완전 삭제(trash -> delete)
    @Transactional
    public void deletePlan(Long planId) {
        Plan plan = planRepository.findPlanById(planId);
        plan.delete();
    }

     // 플랜 완전 삭제(trash -> main)
    @Transactional
    public void revertPlan(Long planId) {
        Plan plan = planRepository.findPlanById(planId);
        plan.revert();
    }

    // 플랜 완성
    public void finishedPlan(Long planId) {
        Plan plan = planRepository.findPlanById(planId);
        plan.finished();
    }


//    public String isFinishedPlan(Long id) {
//        Plan plan = planRepository.findPlanById(id);
//
//        if (plan.getPlanComplete() == PlanComplete.FINISHED) {
//            return "완료된 플랜입니다.";
//        } else {
//            return "redirect:/main-directory";
//        }
//
//    }
}

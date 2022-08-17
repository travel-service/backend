package com.trablock.web.service.plan;

import com.trablock.web.config.jwt.JwtTokenProvider;
import com.trablock.web.controller.form.Form;
import com.trablock.web.controller.form.StateChangeForm;
import com.trablock.web.converter.Converter;
import com.trablock.web.converter.Converter.MainDirectory;
import com.trablock.web.dto.plan.*;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.enumtype.PlanItemStatus;
import com.trablock.web.entity.plan.enumtype.PlanStatus;
import com.trablock.web.global.HTTPStatus;
import com.trablock.web.repository.member.MemberRepository;
import com.trablock.web.repository.plan.PlanItemRepository;
import com.trablock.web.repository.plan.PlanRepository;
import com.trablock.web.service.plan.interfaceC.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final PlanItemRepository planItemRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void savePlan(Plan plan) {
        planRepository.save(plan);
    }

    @Override
    @Transactional
    public Plan createPlan(Form form, Member member) {
        Plan plan = form.getPlanForm().toEntity(member);
        savePlan(plan);
        return plan;
    }

    @Override
    public PlanDto getOnePlanDto(Long planId, Member member) {
        return planRepository.findPlanByMember(planId, member).orElseThrow().toDto();
    }

    @Override
    public Member getMemberFromPayload(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveAccessToken(request);
        String userName = jwtTokenProvider.getUserName(token);
        return memberRepository.findByUserName(userName).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<Plan> findMainPlanDirectoryMain(Member member) {
        Member optionalMember = Optional.ofNullable(member).orElseThrow();
        return planRepository.findPlansByPlanStatus(optionalMember, PlanStatus.MAIN);
    }

    @Override
    // TODO TEST
    public List<Plan> findTrashPlanDirectoryMain(HttpServletRequest request) {
        Member member = Optional.ofNullable(getMemberFromPayload(request)).orElseThrow();
        return planRepository.findPlansByPlanStatus(member, PlanStatus.TRASH);
    }

    // 플랜 삭제(main -> trash)
    @Override
    @Transactional
    public void cancelPlan(StateChangeForm stateChangeForm, Member member) {
        for (int i = 0; i < stateChangeForm.getPlanId().size(); i++) {
            Plan plan = planRepository.findPlanByMember(stateChangeForm.getPlanId().get(i), member).orElseThrow();
            plan.trash();
        }
    }

    // 플랜 완전 삭제(trash -> delete)
    @Override
    @Transactional
    public void deletePlan(StateChangeForm stateChangeForm, Member member) {
        for (int i = 0; i < stateChangeForm.getPlanId().size(); i++) {
            Plan plan = planRepository.findPlanByMember(stateChangeForm.getPlanId().get(i), member).orElseThrow();
            plan.delete();
        }
    }

    // 플랜 복구(trash -> main)
    @Override
    @Transactional
    public void revertPlan(StateChangeForm stateChangeForm, Member member) {
        for (int i = 0; i < stateChangeForm.getPlanId().size(); i++) {
            Plan plan = planRepository.findPlanByMember(stateChangeForm.getPlanId().get(i), member).orElseThrow();
            plan.revert();
        }
    }

    // 플랜 완성
    @Override
    public void finishedPlan(Long planId) {
        Plan plan = planRepository.findPlanById(planId).orElseThrow();
        plan.finished();
    }

    // 플랜 완성 -> 수정
    @Override
    public void unFinishedPlan(Long planId) {
        Plan plan = planRepository.findPlanById(planId).orElseThrow();
        plan.unFinished();
    }

    /**
     * User Plan Update
     *
     * @param planId
     * @param userPlanUpdateDto
     */
    @Override
    @Transactional
    public void updateUserPlanContent(Long planId, Member member, UserPlanUpdateDto userPlanUpdateDto) {
        Plan plan = planRepository.findPlanByMember(planId, member).orElseThrow();
        plan.updatePlan(userPlanUpdateDto);
    }

    /**
     * 플랜 갯수 반환
     *
     * @param member
     * @return
     */
    @Override
    public int countPlan(Member member) {
        return planRepository.planCount(member, PlanStatus.MAIN);
    }

    /**
     * 휴지통 플랜 갯수 반환
     *
     * @param member
     * @return
     */
    @Override
    public int countTrashPlan(Member member) {
        return planRepository.trashPlanCount(member);
    }

    /**
     * SelectedLocation의 locationId를 불러오기 위한 Plan 객체 가져오기
     *
     * @param planId
     * @param member
     * @return
     */
    @Override
    public Plan returnPlan(Long planId, Member member) {
        return planRepository.findPlanByMember(planId, member).orElseThrow();
    }

    /**
     * test
     * @param member
     * @return
     */
    @Override
    public MainDirectory findPlanInfo(Member member, int planCount) {
        List<PlanInfoDto> planInfo = planRepository.findPlanInfoCustom(member.getId());


        List<PlanInfoDto> result = new ArrayList<>();


        for (int i = 0; i < planInfo.size(); i++) {
            List<Long> userDirectoriesIds = planItemRepository.getUserDirectoriesIdByPlanId(planInfo.get(i).getPlanId(), PlanItemStatus.UNDELETE);
            result.add(new PlanInfoDto(
                    planInfo.get(i).getPlanId(),
                    planInfo.get(i).getName(),
                    planInfo.get(i).getPeriods(),
                    planInfo.get(i).getCreatedDate().substring(0, 10),
                    planInfo.get(i).getPlanComplete(),
                    userDirectoriesIds));
        }

        String message = "메인 디렉터리를 정상적으로 불러왔습니다.";
        return new MainDirectory(HTTPStatus.OK.getCode(), message, planCount, result);
    }
}

package com.trablock.web.repository.plan;

import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.PlanItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    Plan findPlanById(Long id);

    @Query("select p from Plan p where p.id = :id and p.member = :memberId")
    List<Plan> findByIdToList(@Param("id") Long id, @Param("memberId") Member member);

    @Query("select p from Plan p where p.id = :planId and p.member = :memberId")
    Plan findPlanByMemberId(@Param("planId") Long id, @Param("memberId") Member member);

    @Query("select p from Plan p where p.member = :memberId and p.planStatus = 'MAIN'")
    List<Plan> findByMainPlanStatus(@Param("memberId") Optional<Member> id);

    @Query("select p from Plan p where p.member = :memberId and p.planStatus = 'TRASH'")
    List<Plan> findByTrashPlanStatus(@Param("memberId") Optional<Member> id);

    @Query("select count(p) from Plan p where p.member = :memberId")
    int planCount (@Param("memberId") Member id);

    @Query("select count(p) from Plan p where p.member = :memberId and p.planStatus = 'TRASH'")
    int trashPlanCount (@Param("memberId") Member id);
}

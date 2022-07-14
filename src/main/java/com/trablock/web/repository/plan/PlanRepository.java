package com.trablock.web.repository.plan;

import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    @Query("select p from Plan p where p.id = :planId")
    Optional<Plan> findPlanById(@Param("planId") Long planId);

    @Query("select p from Plan p where p.id = :planId and p.member = :member")
    List<Plan> findByIdToList(@Param("planId") Long planId, @Param("member") Member member);

    @Query("select p from Plan p where p.id = :planId and p.member = :member")
    Optional<Plan> findPlanByMember(@Param("planId") Long planId, @Param("member") Member member);

    @Query("select p from Plan p where p.member = :member and p.planStatus = 'MAIN'")
    List<Plan> findByMainPlanStatus(@Param("member") Member member);

    @Query("select p from Plan p where p.member = :member and p.planStatus = 'TRASH'")
    List<Plan> findByTrashPlanStatus(@Param("member") Member member);

    @Query("select count(p) from Plan p where p.member = :member")
    int planCount(@Param("member") Member member);

    @Query("select count(p) from Plan p where p.member = :member and p.planStatus = 'TRASH'")
    int trashPlanCount(@Param("member") Member member);
}

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

    @Query("select p from Plan p where p.id = :id")
    List<Plan> findByIdToList(@Param("id") Plan id);

    @Query("select p from Plan p  where p.id = :id")
    Plan findPlanByIdTypePlan(@Param("id") Plan id);

    @Query("select p from Plan p where p.member = :id and p.planStatus = 'MAIN'")
    List<Plan> findByMainPlanStatus(@Param("id") Optional<Member> id);

    @Query("select p from Plan p where p.member = :id and p.planStatus = 'TRASH'")
    List<Plan> findByTrashPlanStatus(@Param("id") Optional<Member> id);

}

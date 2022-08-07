package com.trablock.web.repository.plan;

import com.trablock.web.dto.plan.UserDirectoryIdDto;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.PlanItem;
import com.trablock.web.entity.plan.UserDirectory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlanItemRepository extends JpaRepository<PlanItem, Long>, PlanItemRepositoryCustom {

    // TODO TEST
    List<PlanItem> findPlanItemByUserDirectoryId(Long id);

    // TODO TEST
    @Query("select pi.plan from PlanItem pi where pi.userDirectory = :userDirectoryId")
    List<Plan> findPlanItemByPI(@Param("userDirectoryId") UserDirectory id);

    // TODO TEST
    @Query("select pi from PlanItem pi where pi.userDirectory = :userDirectoryId")
    List<PlanItem> countPlan(@Param("userDirectoryId") UserDirectory id);

    // TODO TEST
    PlanItem findPlanItemById(Long id);

    // TODO TEST
    @Query("select pi from PlanItem pi where pi.userDirectory = :userDirectoryId")
    List<PlanItem> findPlanItemByUDID(@Param("userDirectoryId") Long id);

    // TODO TEST
    @Query("select pi.userDirectory.id from PlanItem pi where pi.plan.id = :planId")
    List<Long> getUserDirectoriesIdByPlanId(@Param("planId") Long planId);

    @Query("select pi.id from PlanItem pi where pi.plan.id in :planId")
    List<UserDirectoryIdDto> findUserDirectoryIdByPlanId(@Param("planId") List<Long> toPlanId);
}

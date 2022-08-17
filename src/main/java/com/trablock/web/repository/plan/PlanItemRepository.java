package com.trablock.web.repository.plan;

import com.trablock.web.dto.plan.PlansDeleteDto;
import com.trablock.web.dto.plan.UserDirectoryIdDto;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.PlanItem;
import com.trablock.web.entity.plan.UserDirectory;
import com.trablock.web.entity.plan.enumtype.PlanItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlanItemRepository extends JpaRepository<PlanItem, Long>, PlanItemRepositoryCustom {

    List<PlanItem> findPlanItemByUserDirectoryId(Long id);

    @Query("select pi.plan from PlanItem pi where pi.userDirectory = :userDirectoryId and pi.planItemStatus = :planItemStatus")
    List<Plan> findPlanItemByPI(@Param("userDirectoryId") UserDirectory id, @Param("planItemStatus")PlanItemStatus planItemStatus);

    @Query("select pi from PlanItem pi where pi.userDirectory = :userDirectoryId and pi.planItemStatus = :planItemStatus")
    List<PlanItem> countPlan(@Param("userDirectoryId") UserDirectory id, @Param("planItemStatus")PlanItemStatus planItemStatus);

    @Query("select pi.userDirectory.id from PlanItem pi where pi.plan.id = :planId and pi.planItemStatus = :planItemStatus")
    List<Long> getUserDirectoriesIdByPlanId(@Param("planId") Long planId, @Param("planItemStatus")PlanItemStatus planItemStatus);

    @Query("select pi from PlanItem pi where pi.userDirectory.id = :userDirectoryId and pi.plan.id = :planId")
    PlanItem getUserDirectoriesIdByPlanIdAndUdir(@Param("userDirectoryId") Long userDirectoryId, @Param("planId") Long planId);
}

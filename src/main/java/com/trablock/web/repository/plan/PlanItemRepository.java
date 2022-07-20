package com.trablock.web.repository.plan;

import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.PlanItem;
import com.trablock.web.entity.plan.UserDirectory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlanItemRepository extends JpaRepository<PlanItem, Long> {

    List<PlanItem> findPlanItemByUserDirectoryId(Long id);

    @Query("select pi.plan from PlanItem pi where pi.userDirectory = :userDirectoryId")
    List<Plan> findPlanItemByPI(@Param("userDirectoryId") UserDirectory id);

    @Query("select count(pi) from PlanItem pi where pi.userDirectory = :userDirectoryId")
    Integer countPlan (@Param("userDirectoryId") UserDirectory id);

    PlanItem findPlanItemById(Long id);

    @Query("select pi from PlanItem pi where pi.userDirectory = :userDirectoryId")
    List<PlanItem> findPlanItemByUDID(@Param("userDirectoryId") Long id);

    @Query("select pi.userDirectory.id from PlanItem pi where pi.plan.id = :planId")
    List<Long> getUserDirectoriesIdByPlanId(@Param("planId") Long planId);
}

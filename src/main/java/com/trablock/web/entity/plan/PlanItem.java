package com.trablock.web.entity.plan;

import com.trablock.web.entity.plan.enumtype.PlanItemStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanItem {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_directory_id")
    private UserDirectory userDirectory;

    @Enumerated(EnumType.STRING)
    private PlanItemStatus planItemStatus;

    public void deletePlan() {
        this.planItemStatus = PlanItemStatus.DELETE;
    }

    public void revertPlan() {
        this.planItemStatus = PlanItemStatus.UNDELETE;
    }
}


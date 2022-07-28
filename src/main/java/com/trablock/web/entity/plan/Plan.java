package com.trablock.web.entity.plan;

import com.trablock.web.dto.plan.PlanDto;
import com.trablock.web.dto.plan.UserPlanUpdateDto;
import com.trablock.web.entity.BaseTimeEntity;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.enumtype.PlanComplete;
import com.trablock.web.entity.plan.enumtype.PlanStatus;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plan extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "plan_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "plan")
    private List<Day> days = new ArrayList<>();

    @OneToMany(mappedBy = "plan")
    private List<SelectedLocation> selectedLocations = new ArrayList<>();

    @OneToMany(mappedBy = "plan")
    private List<Concept> concepts = new ArrayList<>();

    @OneToMany(mappedBy = "plan", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PlanItem> planItems = new ArrayList<>();

    private String name;
    private int periods;
    private String depart;
    private String thumbnail;

    @Enumerated(EnumType.STRING)
    private PlanStatus planStatus;

    @Enumerated(EnumType.STRING)
    private PlanComplete planComplete;

    //==비지니스 로직==//

    /**
     * 플랜 삭제
     */
    public void trash() {
        if (getPlanStatus() == PlanStatus.TRASH) {
            throw new IllegalStateException("이미 휴지통으로 이동된 플랜입니다.");
        } else if (getPlanStatus() == PlanStatus.DELETE) {
            throw new IllegalStateException("삭제된 플랜입니다.");
        }

        this.planStatus = PlanStatus.TRASH;
    }

    /**
     * 플랜 완전 삭제
     */
    public void delete() {
        if (getPlanStatus() == PlanStatus.MAIN) {
            throw new IllegalStateException("휴지통으로 먼저 이동하시오");
        } else if (getPlanStatus() == PlanStatus.DELETE) {
            throw new IllegalStateException("이미 삭제된 플랜입니다.");
        }

        this.planStatus = PlanStatus.DELETE;
    }

    /**
     * 플랜 복구
     */
    public void revert() {
        if (getPlanStatus() == PlanStatus.MAIN) {
            throw new IllegalStateException("이미 복구된 플랜입니다.");
        } else if (getPlanStatus() == PlanStatus.DELETE) {
            throw new IllegalStateException("완전 삭제된 플랜은 복구 할 수 없습니다.");
        }

        this.planStatus = PlanStatus.MAIN;
    }


    /**
     * 플랜 완성
     */
    public void finished() {
        if (getPlanComplete() == PlanComplete.FINISHED) {
            throw new IllegalStateException("이미 완성된 플랜입니다.");
        }

        this.planComplete = PlanComplete.FINISHED;
    }

    /**
     * 플랜 완성 -> 비완성
     */
    public void unFinished() {
        this.planComplete = PlanComplete.UNFINISHED;
    }

    /**
     * Plan Update
     *
     * @param userPlanUpdateDto
     */
    public void updatePlan(UserPlanUpdateDto userPlanUpdateDto) {
        this.depart = userPlanUpdateDto.getDepart();
        this.name = userPlanUpdateDto.getName();
        this.periods = userPlanUpdateDto.getPeriods();
        this.thumbnail = userPlanUpdateDto.getThumbnail();
    }

    public PlanDto toDto() {
        return PlanDto.builder()
                .planId(id)
                .depart(depart)
                .name(name)
                .periods(periods)
                .build();
    }
}

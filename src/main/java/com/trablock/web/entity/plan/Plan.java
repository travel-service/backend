package com.trablock.web.entity.plan;

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
        }

        this.setPlanStatus(PlanStatus.TRASH);
    }

    /**
     * 플랜 삭제
     */
    public void delete() {
        if (getPlanStatus() == PlanStatus.MAIN) {
            throw new IllegalStateException("휴지통으로 먼저 이동하시오");
        }

        this.setPlanStatus(PlanStatus.DELETE);
    }

    /**
     * 플랜 복구
     */
    public void revert() {
        if (getPlanStatus() == PlanStatus.MAIN) {
            throw new IllegalStateException("이미 복구된 플랜입니다.");
        }

        this.setPlanStatus(PlanStatus.MAIN);
    }

    public void setPlanStatus(PlanStatus planStatus) {
        this.planStatus = planStatus;
    }

    /**
     * 플랜 완성
     */
    public void finished() {
        PlanComplete planComplete = PlanComplete.FINISHED;
        this.planComplete = planComplete;
    }
}

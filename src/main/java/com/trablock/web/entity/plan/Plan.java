package com.trablock.web.entity.plan;

import com.trablock.web.entity.BaseEntity;
import com.trablock.web.entity.member.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plan extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "plan_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<Day> days = new ArrayList<>();

    @OneToMany(mappedBy = "plan")
    private List<SelectedLocation> selectedLocations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "main_directory_id")
    private MainDirectory mainDirectory;

    private String concept;
    private String name;
    private String destination;
    private int periods;
    private String depart;
    private String thumbnail;

    @Enumerated(EnumType.STRING)
    private PlanStatus planStatus;

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getPlans().add(this);
    }

    public void addDay(Day day) {
        days.add(day);
    }

    //==생성 메서드==//
//    public static Plan createPlan(Member member, Day... days) {
//        Plan plan = new Plan();
//        plan.setMember(member);
//
//    }
}

package com.trablock.web.entity.plan;

import com.trablock.web.entity.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plan {

    @Id
    @GeneratedValue
    @Column(name = "plan_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<Day> selectedLocations = new ArrayList<>();

    private String concept;
    private String name;
    private String destination;
    private int periods;
    private String depart;

    @Enumerated(EnumType.STRING)
    private PlanStatus status;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "directory")
    private Directory directory;
*/
}

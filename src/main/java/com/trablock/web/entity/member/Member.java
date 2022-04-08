package com.trablock.web.entity.member;

import com.trablock.web.entity.BaseEntity;
import com.trablock.web.entity.BaseTimeEntity;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.SelectedLocation;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "member")
    private List<Plan> plans = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<SelectedLocation> memberSelectedLocations = new ArrayList<>();

    public Member(String name) {
        this.name = name;
    }
}

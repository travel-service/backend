package com.trablock.web.entity.plan;

import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectedLocation {

    @Id @GeneratedValue
    @Column(name = "selected_location_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    //@Enumerated(EnumType.STRING)
    //private SelectStatus selectStatus;
}

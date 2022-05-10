package com.trablock.web.entity.plan;

import com.trablock.web.entity.BaseTimeEntity;
import com.trablock.web.entity.location.Location;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Day extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "day_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location locations;

    private String copyLocationId;
}

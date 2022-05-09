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

    //private int sequence;
    private int days;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location locations;

//    @Embedded
//    private MovingData movingData;

    private String vehicle;
    private String movingTime;
    private String stayTime;
    private String startTime;
    private String copyLocationId;
}

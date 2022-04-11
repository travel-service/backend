package com.trablock.web.entity.plan;

import com.trablock.web.entity.BaseEntity;
import com.trablock.web.entity.location.Location;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Day{

    @Id @GeneratedValue
    @Column(name = "day_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    private int sequence;
    private int days;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location locations;

    @Embedded
    private MovingData movingData;

}

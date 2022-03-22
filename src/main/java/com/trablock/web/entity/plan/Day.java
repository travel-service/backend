package com.trablock.web.entity.plan;

import com.trablock.web.entity.location.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "selected_location")
@NoArgsConstructor
public class Day {

    @Id @GeneratedValue
    @Column(name = "selected_location_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan planId;

    private int days;
    private int sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location locationID;

    @Embedded
    private MovingData movingData;

    @Embedded
    private TimeData timeData;

    @Enumerated(EnumType.STRING)
    private SelectStatus status;
}

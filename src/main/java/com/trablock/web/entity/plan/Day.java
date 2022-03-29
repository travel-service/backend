package com.trablock.web.entity.plan;

import com.trablock.web.entity.location.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "days")
public class Day {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    private int days;
    private int sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location locationID;

    @Embedded
    private MovingData movingData;

    @Enumerated(EnumType.STRING)
    private SelectStatus status;
}

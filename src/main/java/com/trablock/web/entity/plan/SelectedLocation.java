package com.trablock.web.entity.plan;

import com.trablock.web.entity.location.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "selected_location")
@NoArgsConstructor
public class SelectedLocation {

    @Id @GeneratedValue
    @Column(name = "selected_location_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan planId;

    private int days;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location locationID;

    private int sequence;
    private String startTime;
    private String movingTime;
    private String stayTime;

    @Enumerated(EnumType.STRING)
    private Vehicle vehicle;

    @Enumerated(EnumType.STRING)
    private SelectStatus status;
}

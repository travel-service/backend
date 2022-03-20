package com.trablock.web.entity.location;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
@Getter
public class Leports {
    @Id
    private Long id;

    @JoinColumn(name = "LOCATION_ID")
    private Long locationId;

    @Column
    private String openPeriod;

    @Column
    private boolean parking;

    @Column
    private String reservation;

    @Column
    private String restDate;

    @Column
    private String fee;

    @Column
    private String useTime;
}

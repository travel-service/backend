package com.trablock.web.entity.location;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
@Getter
public class Restaurant {

    @Id
    private Long id;

    @JoinColumn(name = "LOCATION_ID")
    private Long locationId;

    @Column
    private String popularMenu;
    @Column
    private String openTime;
    @Column
    private boolean packing;
    @Column
    private boolean parking;
    @Column
    private String restDate;
    @Column
    private boolean smoking;
    @Column
    private String menu;
}

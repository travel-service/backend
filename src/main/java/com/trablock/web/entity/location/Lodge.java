package com.trablock.web.entity.location;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
@Getter
public class Lodge {

    @Id
    private Long id;

    @JoinColumn(name = "LOCATION_ID")
    private Long locationId;

    private String checkInTime;
    private String checkOutTime;
    private boolean cooking;
    private boolean parking;

    private String reservationURL;

    private String subFacility;
    private boolean barbecue;
    private boolean beauty;
    private boolean beverage;
    private boolean bicycle;
    private boolean campfire;
    private boolean fitness;
    private boolean singingRoom;
    private boolean publicBath;
    private boolean publicPC;
    private boolean sauna;
    private boolean seminar;
    private boolean sports;

}

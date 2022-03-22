package com.trablock.web.entity.location.type;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Lodge extends TypeLocation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
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

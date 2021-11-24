package com.trablock.demo.domain.location;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Lodge")
public class LodgeLocation extends SystemLocation {

    private String checkinTime;
    private String checkoutTime;

    @Column(name = "lodge_chk_cooking")
    private String cooking;

    private String parking;
    private String reservation_url;
    private String subFacility;

    private boolean barbecue;
    private boolean beauty;
    private boolean beverage;
    private boolean bicycle;
    private boolean campfire;
    private boolean fitness;
    private boolean singingRoom;
    private boolean publicBath;
    private boolean publicPc;
    private boolean sauna;
    private boolean seminar;
    private boolean sports;


}

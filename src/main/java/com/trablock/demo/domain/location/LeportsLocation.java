package com.trablock.demo.domain.location;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Leports")
public class LeportsLocation extends SystemLocation {

    private String openPeriod;
    private String parking;
    private String parkingFee;
    private String reservation;
    private String restDate;
    private String fee;
    private String useTime;
}

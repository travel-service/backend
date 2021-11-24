package com.trablock.demo.domain.location;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("C")
@Getter
public class CultureLocation extends SystemLocation {

    private String parking;
    private String parkingFee;
    private String restDate;
    private String fee;
    private String useTime;
    private String spendTime;

}

package com.trablock.demo.domain.location;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
@Getter
public class AttractionLocation extends SystemLocation {

    private String parking;
    private String restDate;
    private String useTime;
}

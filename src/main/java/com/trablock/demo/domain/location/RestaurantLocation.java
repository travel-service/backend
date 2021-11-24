package com.trablock.demo.domain.location;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("R")
@Getter
public class RestaurantLocation extends SystemLocation {

    private String popularMenu;
    private String openTime;
    private String packing;
    private String restDate;
    private String smoking;
    private String menu;
}

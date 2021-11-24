package com.trablock.demo.domain.location;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("F")
@Getter
public class FestivalLocation extends SystemLocation {

    private String endDate;
    private String homePage;
    private String place;
    private String startDate;
    private String placeInfo;
    private String playTime;
    private String program;
    private String fee;

}

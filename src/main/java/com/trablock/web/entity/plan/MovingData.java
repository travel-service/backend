package com.trablock.web.entity.plan;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class MovingData {

    private String vehicle;
    private String movingTime;
    private String stayTime;
    private String startTime;

    public MovingData(String vehicle, String movingTime, String stayTime, String startTime) {
        this.vehicle = vehicle;
        this.movingTime = movingTime;
        this.stayTime = stayTime;
        this.startTime = startTime;
    }

    protected MovingData() {}



}

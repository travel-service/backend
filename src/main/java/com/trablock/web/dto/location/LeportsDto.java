package com.trablock.web.dto.location;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class LeportsDto {

    private String openPeriod;
    private boolean parking;
    private String reservation;
    private String restDate;
    private String fee;
    private String useTime;

    @Builder
    public LeportsDto(String openPeriod, boolean parking, String reservation, String restDate, String fee, String useTime) {
        this.openPeriod = openPeriod;
        this.parking = parking;
        this.reservation = reservation;
        this.restDate = restDate;
        this.fee = fee;
        this.useTime = useTime;
    }
}

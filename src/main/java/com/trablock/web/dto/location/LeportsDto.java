package com.trablock.web.dto.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class LeportsDto {

    private String openPeriod;
    private boolean parking;
    private String reservation;
    private String restDate;
    private String fee;
    private String useTime;

}

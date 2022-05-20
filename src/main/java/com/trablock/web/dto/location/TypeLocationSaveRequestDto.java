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
public class TypeLocationSaveRequestDto {
    private Boolean parking;
    private String restDate;
    private String fee;
    private String useTime;
    private String spendTime;
    private String endDate;
    private String homepage;
    private String place;
    private String startDate;
    private String placeInfo;
    private String playTime;
    private String program;
    private String openPeriod;
    private String reservation;
    private String checkInTime;
    private String checkOutTime;
    private Boolean cooking;
    private Integer numOfRooms;
    private String reservationUrl;
    private String subFacility;
    private String popularMenu;
    private String openTime;
    private Boolean packing;
    private String menu;
}

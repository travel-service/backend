package com.trablock.web.dto.location;

import com.trablock.web.entity.location.Coords;
import com.trablock.web.entity.location.LocationType;
import lombok.*;

import static lombok.AccessLevel.*;


public class TypeDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = PROTECTED)
    public static class attractionDto {
        private boolean parking;
        private String restDate;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = PROTECTED)
    public static class cultureDto {
        private boolean parking;
        private String restDate;
        private String fee;
        private String useTime;
        private String spendTime;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = PROTECTED)
    public static class festivalDto {
        private String endDate;
        private String homepage;
        private String place;
        private String startDate;
        private String placeInfo;
        private String playTime;
        private String program;
        private String fee;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = PROTECTED)
    public static class leportsDto {
        private String openPeriod;
        private boolean parking;
        private String reservation;
        private String restDate;
        private String fee;
        private String useTime;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = PROTECTED)
    public static class lodgeDto {
        private String checkInTime;
        private String checkOutTime;
        private boolean cooking;
        private boolean parking;
        private String reservationUrl;
        private String subFacility;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = PROTECTED)
    public static class restaurantDto {
        private String popularMenu;
        private String openTime;
        private boolean packing;
        private boolean parking;
        private String restDate;
        private String menu;
    }
}

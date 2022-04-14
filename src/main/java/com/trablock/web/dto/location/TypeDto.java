package com.trablock.web.dto.location;

import com.trablock.web.entity.location.Coords;
import com.trablock.web.entity.location.LocationType;
import lombok.*;

import static lombok.AccessLevel.*;


public class TypeDto {

    @Getter
    @NoArgsConstructor(access = PROTECTED)
    public static class attractionDto {
        private Long locId;
        private String name;
        private String address1;
        private String address2;
        private Byte image1;
        private Byte image2;
        private String tel;
        private String summary;
        private String report;
        private boolean parking;
        private String restDate;
        private String useTime;

        @Builder
        public attractionDto(Long locId, String name, String address1, String address2,
                             Byte image1, Byte image2, String tel, String summary, String report, boolean parking, String restDate, String useTime) {
            this.locId = locId;
            this.name = name;
            this.address1 = address1;
            this.address2 = address2;
            this.image1 = image1;
            this.image2 = image2;
            this.tel = tel;
            this.summary = summary;
            this.report = report;
            this.parking = parking;
            this.restDate = restDate;
            this.useTime = useTime;
        }
    }


    @Getter
    @NoArgsConstructor(access = PROTECTED)
    public static class cultureDto {
        private Long locId;
        private String name;
        private String address1;
        private String address2;
        private Byte image1;
        private Byte image2;
        private String tel;
        private String summary;
        private String report;
        private boolean parking;
        private String restDate;
        private String fee;
        private String useTime;
        private String spendTime;

        @Builder
        public cultureDto(Long locId, String name, String address1, String address2, Byte image1, Byte image2, String tel, String summary, String report, boolean parking, String restDate, String fee, String useTime, String spendTime) {
            this.locId = locId;
            this.name = name;
            this.address1 = address1;
            this.address2 = address2;
            this.image1 = image1;
            this.image2 = image2;
            this.tel = tel;
            this.summary = summary;
            this.report = report;
            this.parking = parking;
            this.restDate = restDate;
            this.fee = fee;
            this.useTime = useTime;
            this.spendTime = spendTime;
        }
    }

    @Getter
    @NoArgsConstructor(access = PROTECTED)
    public static class festivalDto {
        private Long locId;
        private String name;
        private String address1;
        private String address2;
        private Byte image1;
        private Byte image2;
        private String tel;
        private String summary;
        private String report;
        private String endDate;
        private String homepage;
        private String place;
        private String startDate;
        private String placeInfo;
        private String playTime;
        private String program;
        private String fee;

        @Builder
        public festivalDto(Long locId, String name, String address1, String address2, Byte image1, Byte image2, String tel, String summary, String report, String endDate, String homepage, String place, String startDate, String placeInfo, String playTime, String program, String fee) {
            this.locId = locId;
            this.name = name;
            this.address1 = address1;
            this.address2 = address2;
            this.image1 = image1;
            this.image2 = image2;
            this.tel = tel;
            this.summary = summary;
            this.report = report;
            this.endDate = endDate;
            this.homepage = homepage;
            this.place = place;
            this.startDate = startDate;
            this.placeInfo = placeInfo;
            this.playTime = playTime;
            this.program = program;
            this.fee = fee;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor(access = PROTECTED)
    public static class leportsDto {
        private Long locId;
        private String name;
        private String address1;
        private String address2;
        private Byte image1;
        private Byte image2;
        private String tel;
        private String summary;
        private String report;
        private String openPeriod;
        private boolean parking;
        private String reservation;
        private String restDate;
        private String fee;
        private String useTime;
    }

    @Data
    @Builder
    @NoArgsConstructor(access = PROTECTED)
    public static class lodgeDto {
        private Long locId;
        private String name;
        private String address1;
        private String address2;
        private Byte image1;
        private Byte image2;
        private String tel;
        private String summary;
        private String report;
        private String checkInTime;
        private String checkOutTime;
        private String cooking;
        private boolean parking;
        private String reservationUrl;
        private String subFacility;
    }

    @Data
    @Builder
    @NoArgsConstructor(access = PROTECTED)
    public static class restaurantDto {
        private Long locId;
        private String name;
        private String address1;
        private String address2;
        private Byte image1;
        private Byte image2;
        private String tel;
        private String summary;
        private String report;
        private String popularMenu;
        private String openTime;
        private boolean packing;
        private boolean parking;
        private String restDate;
        private String menu;
    }
}

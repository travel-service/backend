package com.trablock.web.dto;

import com.trablock.web.entity.location.Coords;
import com.trablock.web.entity.location.LocationType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


public class LocationDto {

    @Getter
    @Builder
    public static class simpleLocDto {
        private String name;
        private Coords coords;
        private LocationType type;
    }

    @Getter
    @Builder
    public static class locationDto {
        private String name;
        private String address1;
        private String address2;
        private Byte image1;
        private Byte image2;
        private String tel;
        private String summary;
        private String report;
    }

    public static class attractionDTO {
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

    }

}

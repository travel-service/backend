package com.trablock.web.dto.location;

import com.trablock.web.domain.LocationType;
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

    private Long locationId;
    private String name;
    private String address1;
    private String address2;
    private String image;
    private String tel;
    private String summary;
    private String report;
    private LocationType type;

    private String openPeriod;
    private boolean parking;
    private String reservation;
    private String restDate;
    private String fee;
    private String useTime;

}

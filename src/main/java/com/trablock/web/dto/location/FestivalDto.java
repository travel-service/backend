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
public class FestivalDto {

    private Long locationId;
    private String name;
    private String address1;
    private String address2;
    private String image;
    private String tel;
    private String summary;
    private String report;
    private LocationType type;

    private String endDate;
    private String homepage;
    private String place;
    private String startDate;
    private String placeInfo;
    private String playTime;
    private String program;
    private String fee;

}

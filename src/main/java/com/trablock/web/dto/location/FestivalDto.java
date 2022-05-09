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
public class FestivalDto {

    private String endDate;
    private String homepage;
    private String place;
    private String startDate;
    private String placeInfo;
    private String playTime;
    private String program;
    private String fee;

}

package com.trablock.web.dto.location;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
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

    @Builder
    public FestivalDto(String endDate, String homepage, String place, String startDate, String placeInfo, String playTime, String program, String fee) {
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

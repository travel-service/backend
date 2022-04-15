package com.trablock.web.dto.location;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class CultureDto {
    private boolean parking;
    private String restDate;
    private String fee;
    private String useTime;
    private String spendTime;

    @Builder
    public CultureDto(boolean parking, String restDate, String fee, String useTime, String spendTime) {
        this.parking = parking;
        this.restDate = restDate;
        this.fee = fee;
        this.useTime = useTime;
        this.spendTime = spendTime;
    }
}

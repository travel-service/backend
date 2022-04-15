package com.trablock.web.dto.location;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class LodgeDto {
    private String checkInTime;
    private String checkOutTime;
    private boolean cooking;
    private boolean parking;
    private String reservationUrl;
    private String subFacility;

    @Builder
    public LodgeDto(String checkInTime, String checkOutTime, boolean cooking, boolean parking, String reservationUrl, String subFacility) {
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.cooking = cooking;
        this.parking = parking;
        this.reservationUrl = reservationUrl;
        this.subFacility = subFacility;
    }
}

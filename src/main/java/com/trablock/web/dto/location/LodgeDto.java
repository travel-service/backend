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
public class LodgeDto {

    private String checkInTime;
    private String checkOutTime;
    private boolean cooking;
    private boolean parking;
    private Long numOfRooms;
    private String reservationUrl;
    private String subFacility;

}

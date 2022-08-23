package com.trablock.web.dto.location.type;

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
public class LodgeDto extends TypeLocationDto {

    private Long locationId;
    private String name;
    private Integer areaCode;
    private String address1;
    private String address2;
    private String image;
    private String tel;
    private String summary;
    private String report;
    private LocationType type;

    private String checkInTime;
    private String checkOutTime;
    private boolean cooking;
    private boolean parking;
    private Integer numOfRooms;
    private String reservationUrl;
    private String subFacility;

}

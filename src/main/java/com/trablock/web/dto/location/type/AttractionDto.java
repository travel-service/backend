package com.trablock.web.dto.location.type;

import com.trablock.web.domain.LocationType;
import com.trablock.web.entity.location.Coords;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class AttractionDto extends TypeLocationDto {

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

    private boolean parking;
    private String restDate;
    private String useTime;

}

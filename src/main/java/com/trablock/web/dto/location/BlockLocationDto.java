package com.trablock.web.dto.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.dto.location.type.TypeLocationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class BlockLocationDto {

    private Long locationId;
    private String name;
    private String address1;
    private String address2;
    private String image;
    private LocationType type;
    private String useTime;
    private String restDate;
    private String openTime;

}

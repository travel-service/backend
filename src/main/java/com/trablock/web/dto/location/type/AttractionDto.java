package com.trablock.web.dto.location.type;

import com.trablock.web.domain.LocationType;
import com.trablock.web.dto.location.BlockLocationDto;
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
public class AttractionDto implements TypeLocationDto {

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

    @Override
    public BlockLocationDto toBlockLocationDto() {
        return BlockLocationDto.builder()
                .locationId(locationId)
                .name(name)
                .address1(address1)
                .address2(address2)
                .image(image)
                .type(type)
                .useTime(useTime)
                .restDate(restDate)
                .build();
    }

}

package com.trablock.web.dto.location.save;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trablock.web.domain.LocationType;
import com.trablock.web.entity.location.Coords;
import com.trablock.web.entity.location.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class LocationRequestDto {

    private String name;
    private String address1;
    private String address2;
    private Coords coords;
    private String image;

    @JsonProperty("areaCode")
    private Integer areaCode;

    @JsonProperty("isMember")
    private Boolean isMember;

    private LocationType type;


    public void setMember(Boolean isMember) {
        this.isMember = isMember;
    }

    public Location toEntity() {
        return Location.builder()
                .name(name)
                .address1(address1)
                .address2(address2)
                .coords(coords)
                .image(image)
                .areaCode(areaCode)
                .type(type)
                .isMember(true)
                .build();
    }
}

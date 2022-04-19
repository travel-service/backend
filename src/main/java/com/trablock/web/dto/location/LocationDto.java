package com.trablock.web.dto.location;

import com.trablock.web.entity.location.LocationType;
import lombok.*;

import static lombok.AccessLevel.*;

@Getter
public class LocationDto {

    private final Long id;
    private final String name;
    private final String address1;
    private final String address2;
    private Byte image1;
    private Byte image2;
    private String tel;
    private String summary;
    private String report;
    private LocationType type;

    @Builder
    public LocationDto(Long id, String name, String address1, String address2, Byte image1, Byte image2, String tel, String summary, String report, LocationType type) {
        this.id = id;
        this.name = name;
        this.address1 = address1;
        this.address2 = address2;
        this.image1 = image1;
        this.image2 = image2;
        this.tel = tel;
        this.summary = summary;
        this.report = report;
        this.type = type;
    }
}

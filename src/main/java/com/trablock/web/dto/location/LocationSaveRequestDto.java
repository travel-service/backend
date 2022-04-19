package com.trablock.web.dto.location;

import com.trablock.web.entity.location.Coords;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.location.LocationType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LocationSaveRequestDto {
    private final String name;
    private final String address1;
    private final String address2;
    private final Coords coords;
    private final Byte image;
    private final LocationType type;

    @Builder
    public LocationSaveRequestDto(String name, String address1, String address2,
                                  Coords coords, Byte image, LocationType type) {
        this.name = name;
        this.address1 = address1;
        this.address2 = address2;
        this.coords = coords;
        this.image = image;
        this.type = type;
    }

    public Location toEntity() {
        return Location.builder()
                .name(name)
                .address1(address1)
                .address2(address2)
                .coords(coords)
                .image(image)
                .type(type)
                .build();
    }
}

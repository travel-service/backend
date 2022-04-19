package com.trablock.web.dto.location;


import com.trablock.web.entity.location.Coords;
import com.trablock.web.entity.location.LocationType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SimpleLocationDto {
    private final Long id;
    private final String name;
    private final Coords coords;
    private final LocationType type;

    @Builder
    public SimpleLocationDto(Long id, String name, Coords coords, LocationType type) {
        this.id = id;
        this.name = name;
        this.coords = coords;
        this.type = type;
    }
}

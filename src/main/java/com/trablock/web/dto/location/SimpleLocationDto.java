package com.trablock.web.dto.location;


import com.trablock.web.domain.LocationType;
import com.trablock.web.entity.location.Coords;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SimpleLocationDto {

    private final Long id;
    private final String name;
    private final Coords coords;
    private final LocationType type;

}

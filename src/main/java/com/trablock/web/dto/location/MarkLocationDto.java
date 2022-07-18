package com.trablock.web.dto.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.entity.location.Coords;
import lombok.*;

import static lombok.AccessLevel.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class MarkLocationDto {

    private Long locationId;
    private String name;
    private Coords coords;
    private LocationType type;

}


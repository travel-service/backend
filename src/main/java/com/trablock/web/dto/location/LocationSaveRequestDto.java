package com.trablock.web.dto.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.entity.location.Coords;
import com.trablock.web.entity.location.Location;
import lombok.*;

import static lombok.AccessLevel.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class LocationSaveRequestDto {
    private String name;
    private String address1;
    private String address2;
    private Coords coords;
    private String image;
    private LocationType type;
    
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

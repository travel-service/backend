package com.trablock.web.dto.location;

import com.trablock.web.entity.location.Coords;
import com.trablock.web.entity.location.LocationType;
import lombok.*;

import static lombok.AccessLevel.*;

@Data
@Builder
@NoArgsConstructor(access = PROTECTED)
public class LocationDto {

    private Long id;
    private String name;
    private String address1;
    private String address2;
    private Byte image1;
    private Byte image2;
    private String tel;
    private String summary;
    private String report;
    private LocationType type;
}

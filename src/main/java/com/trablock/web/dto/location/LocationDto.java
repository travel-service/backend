package com.trablock.web.dto.location;

import com.trablock.web.domain.LocationType;
import lombok.*;

import static lombok.AccessLevel.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class LocationDto {

    private Long locationId;
    private String name;
    private String address1;
    private String address2;
    private String image;
    private String tel;
    private String summary;
    private String report;
    private LocationType type;

}

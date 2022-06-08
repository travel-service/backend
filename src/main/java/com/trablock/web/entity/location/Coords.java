package com.trablock.web.entity.location;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static lombok.AccessLevel.*;

@Embeddable
@Builder
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Coords {

    private String latitude;
    private String longitude;

    public Coords(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

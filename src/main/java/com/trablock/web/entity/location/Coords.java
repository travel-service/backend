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

    private Double latitude;
    private Double longitude;

    public Coords(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

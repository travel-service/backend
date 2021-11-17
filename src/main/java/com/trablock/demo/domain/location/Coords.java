package com.trablock.demo.domain.location;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Coords {

    private Long id;
    private Location location;

    private Float latitude;
    private Float longitude;

}

package com.trablock.demo.domain.location;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@NoArgsConstructor
@MappedSuperclass
@SuperBuilder
public abstract class Location {

    @Column(nullable = false)
    private String name;

    private String address1;
    private String address2;

    @Embedded
    @Column(nullable = false)
    private Coords coords;

    @Embedded
    private Information information;

    private LocationType locationType;

}

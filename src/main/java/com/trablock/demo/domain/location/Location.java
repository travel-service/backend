package com.trablock.demo.domain.location;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class Location {

    @Id @GeneratedValue
    @Column(name = "location_id")
    private Long id;

    private String name;
    private String address1;
    private String address2;

    @Embedded
    private Coords coords;

    private byte[] locationImg ;

    @Embedded
    private Information information;
    private LocationType locationType;

}

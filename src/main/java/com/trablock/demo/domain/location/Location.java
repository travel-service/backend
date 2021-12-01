package com.trablock.demo.domain.location;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@NoArgsConstructor
@MappedSuperclass
@SuperBuilder @Getter
public abstract class Location {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address1;
    private String address2;

    @Embedded
    @Column(nullable = false)
    private Coords coords;

    @Embedded
    private Information information;

    @Enumerated(EnumType.STRING)
    private LocationType locationType;

}

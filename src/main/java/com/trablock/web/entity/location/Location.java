package com.trablock.web.entity.location;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Location {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address1;

    @Column(nullable = false)
    private String address2;

    @Embedded
    private Coords coords;

    @Column
    private Byte image;

    @Column
    private LocationType type;

    @Builder
    public Location(String name, String address1, String address2,
                    Coords coords, Byte image, LocationType type) {
        this.name = name;
        this.address1 = address1;
        this.address2 = address2;
        this.coords = coords;
        this.image = image;
        this.type = type;
    }

    public void setType(LocationType type) {
        this.type = type;
    }
}

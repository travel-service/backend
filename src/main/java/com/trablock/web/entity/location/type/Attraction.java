package com.trablock.web.entity.location.type;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Attraction extends TypeLocation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @JoinColumn(name = "LOCATION_ID")
    private Long locationId;

    @Column
    private boolean parking;

    @Column
    private String restDate;


    @Builder
    public Attraction(boolean parking, String restDate) {
        this.parking = parking;
        this.restDate = restDate;
    }
}

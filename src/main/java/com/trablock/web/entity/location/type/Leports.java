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
public class Leports extends TypeLocation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @JoinColumn(name = "LOCATION_ID")
    private Long locationId;

    @Column
    private String openPeriod;

    @Column
    private boolean parking;

    @Column
    private String reservation;

    @Column
    private String restDate;

    @Column
    private String fee;

    @Column
    private String useTime;

    @Builder
    public Leports(Long locationId, String openPeriod, boolean parking, String reservation, String restDate,
                   String fee, String useTime) {
        this.locationId = locationId;
        this.openPeriod = openPeriod;
        this.parking = parking;
        this.reservation = reservation;
        this.restDate = restDate;
        this.fee = fee;
        this.useTime = useTime;
    }
}

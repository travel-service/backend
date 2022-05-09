package com.trablock.web.entity.location.type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Leports extends TypeLocation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @JoinColumn(name = "LOCATION_ID")
    private Long locationId;

    @Column(length = 1000)
    private String openPeriod;

    @Column
    private boolean parking;

    @Column
    private String reservation;

    @Column
    private String restDate;

    @Column(length = 1000)
    private String fee;

    @Column(length = 1000)
    private String useTime;

}

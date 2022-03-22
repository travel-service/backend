package com.trablock.web.entity.location.type;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Culture extends TypeLocation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @JoinColumn(name = "LOCATION_ID")
    private Long locationId;

    private boolean parking;
    private String parkingFee;

    private String restDate;

    private String fee;

    private String useTime;
    private String spendTime;

    @Builder
    public Culture(boolean parking, String parkingFee, String restDate, String fee, String useTime, String spendTime) {
        this.parking = parking;
        this.parkingFee = parkingFee;
        this.restDate = restDate;
        this.fee = fee;
        this.useTime = useTime;
        this.spendTime = spendTime;
    }
}

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
public class Festival extends TypeLocation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @JoinColumn(name = "LOCATION_ID")
    private Long locationId;

    @Column
    private String endDate;

    @Column
    private String homepage;

    @Column
    private String place;

    @Column
    private String startDate;

    @Column
    private String placeInfo;

    @Column
    private String playTime;

    @Column
    private String program;

    @Column
    private String fee;

    @Builder
    public Festival(String endDate, String homepage, String place, String startDate,
                    String placeInfo, String playTime, String program, String fee) {
        this.endDate = endDate;
        this.homepage = homepage;
        this.place = place;
        this.startDate = startDate;
        this.placeInfo = placeInfo;
        this.playTime = playTime;
        this.program = program;
        this.fee = fee;
    }
}

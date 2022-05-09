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

    @Column(length = 1000)
    private String placeInfo;

    @Column(length = 1000)
    private String playTime;

    @Column
    private String program;

    @Column(length = 1000)
    private String fee;

}

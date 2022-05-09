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
public class Restaurant extends TypeLocation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "LOCATION_ID")
    private Long locationId;

    @Column
    private String popularMenu;
    @Column
    private String openTime;
    @Column
    private boolean packing;
    @Column
    private boolean parking;
    @Column
    private String restDate;
    @Column(length = 2000)
    private String menu;

}

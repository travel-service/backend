package com.trablock.demo.domain.location;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.trablock.demo.domain.location.LocationType.TourSpot;

@Getter
@Entity
@NoArgsConstructor
public class MemberLocation implements Location {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;
    private String address1;
    private String address2;

    @Embedded
    private Coords coords;

    private byte[] locationImg;

    @Embedded
    private Information information;

    private  LocationType locationType;
}

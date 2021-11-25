package com.trablock.demo.domain.location;


import com.trablock.demo.domain.SNS;
import lombok.*;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
public abstract class SystemLocation extends Location {

    @Id
    @Column(name = "SYSTEM_LOCATION_ID")
    private Long id;

    private String name;

    private String address1;
    private String address2;

    @Embedded
    private Coords coords;

    @Embedded
    private Information information;

    @Embedded
    private SNS sns;

    @Embedded
    private LocationEnglishInfo englishInfo;

    @Embedded
    private SystemLocationCategory category;
}

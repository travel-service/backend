package com.trablock.demo.domain.location;


import com.trablock.demo.domain.SNS;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
public class SystemLocation extends Location {

    @Id
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

}

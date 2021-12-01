package com.trablock.demo.domain.location;


import com.trablock.demo.domain.SNS;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = PROTECTED)
@Getter
@SuperBuilder
public abstract class SystemLocation extends Location {

//    @Id
//    private Long id;

    @Embedded
    private SNS sns;

    @Embedded
    private LocationEnglishInfo englishInfo;

    @Embedded
    private SystemLocationCategory category;


}

package com.trablock.web.entity.location;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Information {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @JoinColumn(name = "LOCATION_ID")
    private Long locationId;

    @Column
    private String summary;

    @Column
    private Byte image1;

    @Column
    private Byte image2;

    @Column
    private String tel;

    @Builder
    public Information(Long locationId, String summary, Byte image1, Byte image2, String tel) {
        this.locationId = locationId;
        this.summary = summary;
        this.image1 = image1;
        this.image2 = image2;
        this.tel = tel;
    }
}

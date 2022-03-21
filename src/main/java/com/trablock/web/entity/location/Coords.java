package com.trablock.web.entity.location;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Coords {
    @Column
    private Float latitude;

    @Column
    private Float longitude;
}

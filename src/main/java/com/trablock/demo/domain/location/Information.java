package com.trablock.demo.domain.location;

import javax.persistence.Embeddable;

@Embeddable
public class Information {

    private Long id;
    private Location location;
    private String summary;
    private String information;

}

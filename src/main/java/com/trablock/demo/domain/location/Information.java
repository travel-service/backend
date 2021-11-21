package com.trablock.demo.domain.location;

import lombok.AllArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
public class Information {

    private Location location;
    private String summary;
    private String information;
    private String tel;

    protected Information() {
    }

}

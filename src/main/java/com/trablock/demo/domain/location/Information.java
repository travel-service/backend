package com.trablock.demo.domain.location;

import lombok.AllArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
public class Information {

    //private Location location;
    private String summary;
    private String report;
    private Byte[] image1;
    private Byte[] image2;
    private String tel;

    protected Information() {
    }

}

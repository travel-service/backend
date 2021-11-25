package com.trablock.demo.domain.location;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class SystemLocationCategory {
    private String majorCategory;
    private String middleCategory;
    private String minorCategory;
    private int areaCode;
    private int districtCode;
    private int zipCode;
}

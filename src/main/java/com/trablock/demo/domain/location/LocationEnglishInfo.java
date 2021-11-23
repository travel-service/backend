package com.trablock.demo.domain.location;

import lombok.Getter;
import javax.persistence.Embeddable;

@Getter @Embeddable
public class LocationEnglishInfo {

    private String EngSummary;
    private String EngInformation;
    private String EngAddress;
}

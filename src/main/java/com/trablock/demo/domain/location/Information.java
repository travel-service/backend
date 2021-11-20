package com.trablock.demo.domain.location;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Information {

    //private Location location; 데이터 겹쳐서 주석
    private String summary;
    private String information;

}

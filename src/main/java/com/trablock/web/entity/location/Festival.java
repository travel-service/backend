package com.trablock.web.entity.location;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
@Getter
public class Festival {

    @Id
    private Long id;

    @JoinColumn(name = "LOCATION_ID")
    private Long locationId;

    @Column
    private String endDate;
    
    @Column
    private String homepage;

    @Column
    private String place;

    @Column
    private String startDate;

    @Column
    private String placeInfo;

    @Column
    private String playTime;

    @Column
    private String program;

    @Column
    private String fee;

}

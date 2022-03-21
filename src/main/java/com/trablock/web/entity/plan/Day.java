package com.trablock.web.entity.plan;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Day {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    private String startTime;
    private String movingTime;
    private String stayTime;

    @Enumerated(EnumType.STRING)
    private Vehicle vehicle;




}

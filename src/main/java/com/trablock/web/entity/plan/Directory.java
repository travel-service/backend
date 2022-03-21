package com.trablock.web.entity.plan;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Directory {

    @Id
    @GeneratedValue
    private Long canvasId;


}

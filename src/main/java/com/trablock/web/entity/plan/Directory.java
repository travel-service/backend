package com.trablock.web.entity.plan;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Directory {

    @Id @GeneratedValue
    private Long canvasId;

    /*@OneToMany(mappedBy = "plan")
    private List<Plan> plans = new ArrayList<>();*/
}

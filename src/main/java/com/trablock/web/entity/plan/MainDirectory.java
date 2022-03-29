package com.trablock.web.entity.plan;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class MainDirectory {

    @Id @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "mainDirectory")
    private List<Plan> plans = new ArrayList<>();
}

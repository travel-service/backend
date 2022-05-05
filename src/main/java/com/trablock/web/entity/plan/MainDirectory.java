package com.trablock.web.entity.plan;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainDirectory {

    @Id @GeneratedValue
    @Column(name = "main_directory_id")
    private Long id;

    private String name;
}


package com.trablock.web.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class SNS {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long FKid;

    @Column
    private String instagram;

    @Column
    private String facebook;

    @Column
    private String homepage;

    @Builder
    public SNS(String instagram, String facebook, String homepage) {
        this.instagram = instagram;
        this.facebook = facebook;
        this.homepage = homepage;
    }
}

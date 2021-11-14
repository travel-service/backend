package com.trablock.demo.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

/**
 * 구현 못함, 작동 안함
 */
@Entity
@Getter
@Table(name = "profile")
public class Profile {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @OneToOne(mappedBy = "profile")
    private Member member;

    @Column(unique = true)
    private String nickname;

    /**
    이미지를 어떻게 다룰것 인가? 학습 필요
     */

}

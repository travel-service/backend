package com.trablock.demo.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/** 섬세한 부분이 아직 부족, 회원 가입 시 1개의 Personinfo가 할당, 2개 이상 할당 안되게 막아야함 (숙제) */
@Entity
@Getter
@NoArgsConstructor
public class PersonInfo {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String birthday;
    private Gender gender;
    private String phoneNum;
    private String email;
    private Boolean termsOfUse;

    public PersonInfo(String birthday, Gender gender, String phoneNum, String email, Boolean termsOfUse) {
        this.birthday = birthday;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.email = email;
        this.termsOfUse = termsOfUse;
    }
}

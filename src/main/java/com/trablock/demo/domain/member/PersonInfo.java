package com.trablock.demo.domain.member;

import lombok.Getter;

import javax.persistence.Embeddable;


@Embeddable
@Getter
public class PersonInfo {
    private String birthday;
    private Gender gender;
    private String phonenum;
    private String email;

    protected PersonInfo() {} // JPA 스펙상 생성, 만질 필요 X

    public PersonInfo(String birthday, Gender gender, String phonenum, String email){
        this.birthday = birthday;
        this.gender = gender;
        this.phonenum = phonenum;
        this.email = email;
    }
}

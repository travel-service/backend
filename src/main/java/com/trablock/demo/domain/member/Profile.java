package com.trablock.demo.domain.member;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Profile {

    private String nickname;
    private byte[] memberimg;

    protected Profile() {} //JPA 스펙상 생성, 만질 필요 X

    public Profile(String nickname, byte[] memberimg) {
        this.nickname = nickname;
        this.memberimg = memberimg;
    }

}

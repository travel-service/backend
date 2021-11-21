package com.trablock.demo.domain.member;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Profile {

    private String nickname;
    private byte[] memberImg;

    protected Profile() {} //JPA 스펙상 생성, 만질 필요 X

    public Profile(String nickname, byte[] memberImg) {
        this.nickname = nickname;
        this.memberImg = memberImg;
    }

}

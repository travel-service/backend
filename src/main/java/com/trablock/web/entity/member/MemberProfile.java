package com.trablock.web.entity.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
public class MemberProfile {
    private String nickName;
    private byte[] memberImg;

    protected MemberProfile() {}
}

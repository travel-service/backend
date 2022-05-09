package com.trablock.web.entity.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
@Getter
public class MemberInfo {

    private String birthday;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    private String phoneNum;
    private String email;

    //private boolean termsOfUse; 이게 뭔지 까먹었습니다.
}

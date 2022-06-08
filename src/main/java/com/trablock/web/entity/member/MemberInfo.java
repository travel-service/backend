package com.trablock.web.entity.member;

import lombok.*;

import javax.persistence.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
@Getter
@Setter
public class MemberInfo {

    private String birthday;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    private String phoneNum;
    private String email;

    //private boolean termsOfUse; 이게 뭔지 까먹었습니다.
}

package com.trablock.web.dto.member;

import com.trablock.web.entity.member.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberDto {
    private String nickName;
    private String memberImg;
    private String comment;
    private String birthday;
    private Gender gender;
    private String phoneNum;
    private String email;
}

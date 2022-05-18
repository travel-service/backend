package com.trablock.web.dto.member;

import com.trablock.web.entity.member.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfoDto {
    private String birthday;
    private Gender gender;
    private String phoneNum;
    private String email;
}

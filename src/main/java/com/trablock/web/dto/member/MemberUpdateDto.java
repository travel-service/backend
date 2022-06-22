package com.trablock.web.dto.member;

import com.trablock.web.entity.member.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class MemberUpdateDto {
    private String nickName;
    private String bio;
    private String birthday;
    private Gender gender;
    private String email;
}

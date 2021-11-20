package com.trablock.demo.dto.member;

import com.trablock.demo.domain.member.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.trablock.demo.domain.member.Gender.MALE;
import static com.trablock.demo.domain.member.Gender.UNKNOWN;

/** Setter 없앨 예정 */
@Getter @Setter
@NoArgsConstructor
public class MemberSaveRequestDto {
    private String username;
    private String password;
    private MemberRole role;
    private Profile profile;
    private PersonInfo personInfo = new PersonInfo(null, UNKNOWN, null, null, Boolean.TRUE); /** 회원가입 시 default 값, 성별 모름, 개인정보 동의는 TRUE */

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(password)
                .role(MemberRole.USER)
                .profile(profile)
                .personInfo(personInfo).build();
    }
}

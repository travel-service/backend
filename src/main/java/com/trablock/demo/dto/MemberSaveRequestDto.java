package com.trablock.demo.dto;

import com.trablock.demo.domain.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/** Setter 없앨 예정 */
@Getter @Setter
@NoArgsConstructor
public class MemberSaveRequestDto {
    private String username;
    private String password;
    private MemberRole role;
    private Profile profile;
    private PersonInfo personInfo;

    @Builder
    public MemberSaveRequestDto(String username, String password, MemberRole role, Profile profile, PersonInfo personInfo) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.profile = profile;
        this.personInfo = personInfo;
    }

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(password)
                .role(MemberRole.USER)
                .profile(profile)
                .personInfo(personInfo).build();
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

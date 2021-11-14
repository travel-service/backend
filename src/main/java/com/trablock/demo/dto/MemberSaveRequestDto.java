package com.trablock.demo.dto;

import com.trablock.demo.domain.Member;
import com.trablock.demo.domain.MemberRole;
import com.trablock.demo.domain.Profile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * EntityManager 없이 구현함, 학습 후 수정 예정
 */
@Getter @Setter
@NoArgsConstructor
public class MemberSaveRequestDto {
    private String username;
    private String password;
    private String email;
    private MemberRole role;
    private Profile profile; //아직 구현 못함

    @Builder
    public MemberSaveRequestDto(String username, String password, String email, MemberRole role, Profile profile) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.profile = profile;
    }

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(MemberRole.USER).build();
    }
}

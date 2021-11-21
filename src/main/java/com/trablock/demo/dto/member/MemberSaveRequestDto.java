package com.trablock.demo.dto.member;

import com.trablock.demo.domain.member.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

import static com.trablock.demo.domain.member.Gender.MALE;
import static com.trablock.demo.domain.member.Gender.UNKNOWN;

/** 구조적으로 이 방법이 맞나 회의필요...
 *  회원가입 API에 맞춰 필요한 데이터는 다 넣었습니다.
 * */
@Getter @Setter
@NoArgsConstructor
public class MemberSaveRequestDto {
    private String userId;
    private String password;
    private String passwordCheck;
    private String username;
    private MemberRole role;
    private String nickname;
    private String birthday;
    private Gender gender; // MALE = 0, FEMALE = 1, UNKNOWN = 3, 프론트 구현에 따라 변경가능
    private String phoneNum;
    private String email;


    /** 기능은 문제 X, But 리팩토링이 필요... */
    public Member toEntity() {
        return Member.builder()
                .userId(userId)
                .password(password)
                .username(username)
                .role(MemberRole.USER)
                .profile(new Profile(nickname, null))
                .personInfo(new PersonInfo(birthday, gender, phoneNum, email, Boolean.TRUE)).build();
    }
}

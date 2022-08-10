package com.trablock.web.dto.member;

import com.trablock.web.entity.member.MemberInfo;
import com.trablock.web.entity.member.MemberProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    private int status;
    private String message;
    private Object result;

    public MemberResponseDto successMemberSignUp(String nickname) {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.CREATED.value());
        res.setMessage("회원 가입이 정상적으로 이루어졌습니다.");

        // 반환 데이터 확장성을 고려하여 Map 이용
        Map<String, Object> map = new HashMap<>();
        map.put("nickname", nickname);

        res.setResult(map);
        return res;
    }

    public MemberResponseDto failMemberSignUp() {
        MemberResponseDto res = new MemberResponseDto();

        res.setStatus(HttpStatus.BAD_REQUEST.value());
        res.setMessage("중복된 아이디 입니다.");
        return res;
    }

    public MemberResponseDto successEmailAuth() {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.ACCEPTED.value());
        res.setMessage("이메일 인증이 완료 되었습니다.");

        return res;
    }

    public MemberResponseDto failEmailAuth() {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
        res.setMessage("만료된 인증 URI or 잘못된 URI 입니다. 다시 시도해주세요");

        return res;
    }

    public MemberResponseDto duplicateUserName() {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.CONFLICT.value());
        res.setMessage("이미 존재하는 아이디 입니다.");

        return res;
    }

    public MemberResponseDto canUseUserName() {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.OK.value());
        res.setMessage("사용 가능한 아이디 입니다");

        return res;
    }

    public MemberResponseDto invalidUserNameOrPwd() {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.BAD_REQUEST.value());
        res.setMessage("잘못된 아이디 또는 비밀번호 입니다.");

        return res;
    }

    public MemberResponseDto notFoundUserName() {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.NOT_FOUND.value());
        res.setMessage("존재하지 않는 아이디니다.");

        return res;
    }

    public MemberResponseDto successLogin(String nickname) {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.OK.value());
        res.setMessage("로그인 성공");

        // 확장성을 고려하여 Map 이용
        Map<String, Object> map = new HashMap<>();
        map.put("nickName", nickname);
        res.setResult(map);

        return res;
    }

    public MemberResponseDto successIssuePwd() {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.CREATED.value());
        res.setMessage("입력한 이메일로 임시 비밀번호가 발급되었습니다.");
        return res;
    }

    public MemberResponseDto failIssuePwd() {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.BAD_REQUEST.value());
        res.setMessage("입력한 이메일이 일치하지 않습니다.");

        return res;
    }

    public MemberResponseDto notFoundAccessToken() {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.NOT_FOUND.value());
        res.setMessage("AccessToken이 없습니다.");

        return res;
    }

    public MemberResponseDto expireToken() {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.setMessage("Token이 만료 되었습니다.");

        return res;
    }

    public MemberResponseDto notFoundRefreshToken() {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.NOT_FOUND.value());
        res.setMessage("RefreshToken이 없습니다.");

        return res;
    }

    public MemberResponseDto successCreateToken() {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.CREATED.value());
        res.setMessage("토큰이 재발급 되었습니다.");

        return res;
    }

    public MemberResponseDto duplicateNickName() {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.CONFLICT.value());
        res.setMessage("이미 존재하는 닉네임 입니다.");

        return res;
    }

    public MemberResponseDto canUseNickName() {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.OK.value());
        res.setMessage("사용 가능한 닉네임 입니다");

        return res;
    }

    public MemberResponseDto successLogout() {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.OK.value());
        res.setMessage("정상적으로 로그아웃 되었습니다.");

        return res;
    }

    public MemberResponseDto successGetMemberPage(String nickname, String bio) {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.OK.value());
        res.setMessage("정상적으로 회원 정보를 불러옵니다");

        // 확장성을 고려하여 Map 사용
        Map<String, Object> map = new HashMap<>();
        map.put("nickname", nickname);
        map.put("bio", bio);
        res.setResult(map);

        return res;
    }

    public MemberResponseDto successGetMemberEditPage(MemberProfile profile, MemberInfo info) {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.OK.value());
        res.setMessage("정상적으로 회원 정보를 불러옵니다");

        // 확장성을 고려하여 Map 사용
        Map<String, Object> map = new HashMap<>();
        map.put("profile", profile);
        map.put("info", info);
        res.setResult(map);

        return res;
    }

    public MemberResponseDto successChangeMemberImg() {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.CREATED.value());
        res.setMessage("정상적으로 사진이 변경되었습니다.");

        return res;
    }

    public MemberResponseDto failChangeMemberImg() {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        res.setMessage("사진 변경 실패.");

        return res;
    }

    public MemberResponseDto successEditMemberInfo(MemberProfile profile, MemberInfo info) {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.CREATED.value());
        res.setMessage("회원정보가 변경되었습니다.");

        Map<String, Object> map = new HashMap();
        map.put("profile", profile);
        map.put("info", info);
        res.setResult(map);

        return res;
    }

    public MemberResponseDto successEditMemberProfile(MemberProfile bio) {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.CREATED.value());
        res.setMessage("성공적으로 변경되었습니다.");
        res.setResult(bio);

        return res;
    }


    public MemberResponseDto successEditMemberPwd() {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.CREATED.value());
        res.setMessage("성공적으로 변경되었습니다.");

        return res;
    }

    public MemberResponseDto failEditMemberPwd() {
        MemberResponseDto res = new MemberResponseDto();
        res.setStatus(HttpStatus.BAD_REQUEST.value());
        res.setMessage("잘못된 비밀번호 입니다.");

        return res;
    }

}

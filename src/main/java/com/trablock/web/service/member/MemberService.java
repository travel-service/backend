package com.trablock.web.service.member;

import com.trablock.web.dto.mail.EmailAuthDto;
import com.trablock.web.dto.member.MemberPwdDto;
import com.trablock.web.dto.member.MemberSaveDto;
import com.trablock.web.dto.member.MemberUpdateDto;
import com.trablock.web.entity.member.LoginForm;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.Map;

public interface MemberService {
    String memberSignUp(MemberSaveDto memberSaveDto);

    String confirmEmail(EmailAuthDto requestDto);

    String memberLogin(LoginForm loginForm, HttpServletResponse response);

    ResponseEntity<?> memberLogout(HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<?> getMemberPage(HttpServletRequest request);

    ResponseEntity<?> getMemberImg(HttpServletRequest request) throws FileNotFoundException;

    ResponseEntity<?> memberEditPage(HttpServletRequest request);

    void updateComment(String bio, HttpServletRequest request);

    void updateMember(MemberUpdateDto memberUpdateDto, HttpServletRequest request);

    boolean getTmpPassword(Map<String, String> userInfo);

    ResponseEntity<?> getMemberInfo(HttpServletRequest request);

    ResponseEntity<?> memberRefreshToAccess(HttpServletRequest request, HttpServletResponse response);

    void updateMemberPwd(HttpServletRequest request, MemberPwdDto memberPwdDto);

    boolean memberValidation(String userName);

    boolean checkValidNickName(String nickname);
}

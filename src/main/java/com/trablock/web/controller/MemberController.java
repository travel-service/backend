package com.trablock.web.controller;

import com.trablock.web.config.jwt.JwtTokenService;
import com.trablock.web.dto.mail.EmailAuthDto;
import com.trablock.web.dto.member.MemberPwdDto;
import com.trablock.web.dto.member.MemberSaveDto;
import com.trablock.web.dto.member.MemberUpdateDto;
import com.trablock.web.entity.member.*;
import com.trablock.web.service.file.FileService;
import com.trablock.web.service.member.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberServiceImpl memberServiceImpl;
    private final FileService fileService;
    private final JwtTokenService jwtTokenService;

    // 비회원 - 회원가입
    @PostMapping("/api/signup")
    public String signup(@RequestBody MemberSaveDto signupForm) {
        return memberServiceImpl.MemberSignUp(signupForm);
    }

    // 이메일 인증
    @GetMapping("/api/sign/confirm-email")
    public String confirmEmail(@ModelAttribute EmailAuthDto requestDto) {
        return memberServiceImpl.confirmEmail(requestDto);
    }
    // 비회원 - 중복 ID 체크
    @GetMapping("/api/check-id")
    public boolean check(@RequestParam("userName") String userName) {
        return memberServiceImpl.checkValidUserName(userName);
    }

    // 비회원 - 비밀번호 찾기 (임시비밀번호 발급)
    @PostMapping("/api/find-pwd")
    public boolean findUserPwd(@RequestBody Map<String, String> userInfo) {
        return memberServiceImpl.getTmpPassword(userInfo);
    }

    // 비회원 - 로그인
    @PostMapping("/api/login")
    public String login(@RequestBody LoginForm loginForm, HttpServletResponse response) {
        return memberServiceImpl.MemberLogin(loginForm, response);
    }

    // 비회원 - 사용자 정보 가져오기
    @GetMapping("/api/info")
    public ResponseEntity<?> getInfo(HttpServletRequest request) {
        return memberServiceImpl.getMemberInfo(request);
    }

    // 비회원 - Refresh To Access
    @GetMapping("/api/refresh")
    public ResponseEntity<?> getAccessToken(HttpServletRequest request, HttpServletResponse response) {
        return memberServiceImpl.memberRefreshToAccess(request, response);
    }

    // 회원 - 로그아웃
    @DeleteMapping("/api/user/logout")
    public String logout(HttpServletRequest request) {
        return memberServiceImpl.MemberLogout(request);
    }

    // 회원 - 회원 개인페이지 필요 DATA + (여행 디렉토리도 추가 예정)
    @GetMapping("/api/user/my-page")
    public ResponseEntity<?> getMemberPage(HttpServletRequest request) {
        return memberServiceImpl.getMemberPage(request);
    }

    // 회원 - 회원 개인페이지 프로필 사진
    @GetMapping("/api/user/my-page/img")
    public ResponseEntity<?> getMemberImg(HttpServletRequest request) throws FileNotFoundException {
        return memberServiceImpl.getMemberImg(request);
    }

    // 회원 - 개인정보 수정페이지 필요 DATA
    @GetMapping("/api/user/my-page/edit")
    public ResponseEntity<?> getMemberEditPage(HttpServletRequest request) {
        return memberServiceImpl.MemberEditPage(request);
    }

    // 회원 - 프로필 사진 업데이트
    @PostMapping("/api/user/profile/img")
    public String updateProfileImg(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        return fileService.saveProfileImg(multipartFile, jwtTokenService.TokenToUserName(request));
    }

    // 회원 - 개인정보 수정
    @PutMapping("/api/user/profile/edit")
    public void editMemberProfile(@RequestBody MemberUpdateDto memberUpdateDto, HttpServletRequest request) {
        memberServiceImpl.updateMember(memberUpdateDto, request);
    }

    // 회원 - 한 줄 소개 수정 - 사용보류
    @PutMapping("/api/user/profile/edit/{bio}")
    public void updateComment(@PathVariable("bio") String bio, HttpServletRequest request) {
        memberServiceImpl.UpdateComment(bio, request);
    }

    // 회원 - 비밀번호 수정
    @PutMapping("/api/user/profile/edit/pwd")
    public void updateMemberPwd(HttpServletRequest request, @RequestBody MemberPwdDto memberPwdDto) {
        memberServiceImpl.updateMemberPwd(request, memberPwdDto);
    }

}

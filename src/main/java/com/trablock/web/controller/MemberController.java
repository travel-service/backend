package com.trablock.web.controller;

import com.trablock.web.config.jwt.JwtTokenService;
import com.trablock.web.dto.mail.EmailAuthDto;
import com.trablock.web.dto.member.MemberPwdDto;
import com.trablock.web.dto.member.MemberSaveDto;
import com.trablock.web.dto.member.MemberUpdateDto;
import com.trablock.web.entity.member.*;
import com.trablock.web.service.file.FileService;
import com.trablock.web.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;
    private final FileService fileService;
    private final JwtTokenService jwtTokenService;

    // 비회원 - 회원가입
    @PostMapping("/api/signup")
    public ResponseEntity<String> signup(@RequestBody MemberSaveDto signupForm) {
        return ResponseEntity.ok().body(memberService.memberSignUp(signupForm));
    }

    // 이메일 인증
    @GetMapping("/auth/email")
    public String confirmEmail(@ModelAttribute EmailAuthDto requestDto) {
        return memberService.confirmEmail(requestDto);
    }
    // 비회원 - 중복 ID 체크
    @GetMapping("/api/username/{username}")
    public ResponseEntity<Boolean> checkId(@PathVariable("username") String userName) {
        return ResponseEntity.ok().body(memberService.memberValidation(userName));
    }

    // 비회원 - 비밀번호 찾기 (임시비밀번호 발급)
    @PostMapping("/api/password")
    public ResponseEntity<Boolean> findUserPwd(@RequestBody Map<String, String> userInfo) {
        return ResponseEntity.ok().body(memberService.getTmpPassword(userInfo));
    }

    // 비회원 - 로그인
    @PostMapping("/api/login")
    public String login(@RequestBody LoginForm loginForm, HttpServletResponse response) {
        return memberService.memberLogin(loginForm, response);
    }

    // 비회원 - 사용자 정보 가져오기
    @GetMapping("/auth/status")
    public ResponseEntity<Map<String, Object>> getInfo(HttpServletRequest request) {
        return ResponseEntity.ok().body(memberService.getMemberInfo(request));
    }

    // 비회원 - Refresh To Access
    @GetMapping("/auth/refresh")
    public ResponseEntity<Boolean> getAccessToken(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok().body(memberService.memberRefreshToAccess(request, response));
    }

    // 공통 - 닉네임 중복 검사 (회원가입 시, 닉네임 변경 시)
    @GetMapping("/api/nickname/{nickname}")
    public ResponseEntity<Boolean> checkNickName(@PathVariable("nickname") String nickname) {
        return ResponseEntity.ok().body(memberService.checkValidNickName(nickname));
    }

    // 회원 - 로그아웃
    @PostMapping("/members/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok().body(memberService.memberLogout(request, response));
    }

    // 회원 - 회원 개인페이지 필요 DATA + (여행 디렉토리도 추가 예정)
    @GetMapping("/members/my-page")
    public ResponseEntity<Map<String, Object>> getMemberPage(HttpServletRequest request) {
        return ResponseEntity.ok().body(memberService.getMemberPage(request));
    }

    // 회원 - 회원 개인페이지 프로필 사진
    @GetMapping("/members/my-page/img")
    public ResponseEntity<Resource> getMemberImg(HttpServletRequest request) throws FileNotFoundException {
        return memberService.getMemberImg(request);
    }

    // 회원 - 개인정보 수정페이지 필요 DATA
    @GetMapping("/members/profile/edit")
    public ResponseEntity<Map<String, Object>> getMemberEditPage(HttpServletRequest request) {
        return ResponseEntity.ok().body(memberService.memberEditPage(request));
    }

    // 회원 - 프로필 사진 업데이트
    @PostMapping("/members/profile/img")
    public String updateProfileImg(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        return fileService.saveProfileImg(multipartFile, jwtTokenService.tokenToUserName(request));
    }

    // 회원 - 개인정보 수정
    @PostMapping("/members/profile/edit")
    public void editMemberProfile(@RequestBody MemberUpdateDto memberUpdateDto, HttpServletRequest request) {
        memberService.updateMember(memberUpdateDto, request);
    }

    // 회원 - 한 줄 소개 수정 - 사용보류
    @PostMapping("/members/profile/edit/{bio}")
    public void updateComment(@PathVariable("bio") String bio, HttpServletRequest request) {
        memberService.updateComment(bio, request);
    }

    // 회원 - 비밀번호 수정
    @PostMapping("/members/profile/password")
    public void updateMemberPwd(HttpServletRequest request, @RequestBody MemberPwdDto memberPwdDto) {
        memberService.updateMemberPwd(request, memberPwdDto);
    }

}

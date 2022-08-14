package com.trablock.web.controller;

import com.trablock.web.config.jwt.JwtTokenService;
import com.trablock.web.dto.mail.EmailAuthDto;
import com.trablock.web.dto.member.MemberPwdDto;
import com.trablock.web.dto.member.MemberResponseDto;
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
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberSaveDto memberSaveDto) {
        return memberService.memberSignUp(memberSaveDto);
    }

    // 이메일 인증
    @GetMapping("/auth/email")
    public ResponseEntity<MemberResponseDto> confirmEmail(@ModelAttribute EmailAuthDto requestDto) {
        return memberService.confirmEmail(requestDto);
    }
    // 비회원 - 중복 ID 체크
    @GetMapping("/api/username/{username}")
    public ResponseEntity<MemberResponseDto> checkId(@PathVariable("username") String userName) {
        return memberService.memberValidation(userName);
    }

    // 비회원 - 비밀번호 찾기 (임시비밀번호 발급)
    @PostMapping("/api/password")
    public ResponseEntity<MemberResponseDto> findUserPwd(@RequestBody Map<String, String> userInfo) {
        return memberService.getTmpPassword(userInfo);
    }

    // 비회원 - 로그인
    @PostMapping("/api/login")
    public ResponseEntity<MemberResponseDto> login(@RequestBody LoginForm loginForm, HttpServletResponse response) {
        return memberService.memberLogin(loginForm, response);
    }

    // 비회원 - 사용자 정보 가져오기
    @GetMapping("/auth/status")
    public ResponseEntity<MemberResponseDto> getInfo(HttpServletRequest request) {
        return memberService.getMemberInfo(request);
    }

    // 비회원 - Refresh To Access
    @GetMapping("/auth/refresh")
    public ResponseEntity<MemberResponseDto> getAccessToken(HttpServletRequest request, HttpServletResponse response) {
        return memberService.memberRefreshToAccess(request, response);
    }

    // 공통 - 닉네임 중복 검사 (회원가입 시, 닉네임 변경 시)
    @GetMapping("/api/nickname/{nickname}")
    public ResponseEntity<MemberResponseDto> checkNickName(@PathVariable("nickname") String nickname) {
        return memberService.checkValidNickName(nickname);
    }

    // 회원 - 로그아웃
    @PostMapping("/members/logout")
    public ResponseEntity<MemberResponseDto> logout(HttpServletRequest request, HttpServletResponse response) {
        return memberService.memberLogout(request, response);
    }

    // 회원 - 회원 개인페이지 필요 DATA + (여행 디렉토리도 추가 예정)
    @GetMapping("/members/my-page")
    public ResponseEntity<MemberResponseDto> getMemberPage(HttpServletRequest request) {
        return memberService.getMemberPage(request);
    }

    // 회원 - 회원 개인페이지 프로필 사진
    @GetMapping("/members/my-page/img")
    public ResponseEntity<Object> getMemberImg(HttpServletRequest request) throws FileNotFoundException {
        return memberService.getMemberImg(request);
    }

    // 회원 - 개인정보 수정페이지 필요 DATA
    @GetMapping("/members/profile/edit")
    public ResponseEntity<MemberResponseDto> getMemberEditPage(HttpServletRequest request) {
        return memberService.memberEditPage(request);
    }

    // 회원 - 프로필 사진 업데이트
    @PostMapping("/members/profile/img")
    public ResponseEntity<MemberResponseDto> updateProfileImg(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        return fileService.saveProfileImg(multipartFile, jwtTokenService.tokenToUserName(request));
    }

    // 회원 - 개인정보 수정
    @PostMapping("/members/profile/edit")
    public ResponseEntity<MemberResponseDto> editMemberProfile(@RequestBody MemberUpdateDto memberUpdateDto, HttpServletRequest request) {
        return memberService.updateMember(memberUpdateDto, request);
    }

    // 회원 - 한 줄 소개 수정
    @PostMapping("/members/profile/bio")
    public ResponseEntity<MemberResponseDto> updateComment(@RequestBody Map<String, String> bio, HttpServletRequest request) {
        return memberService.updateComment(bio, request);
    }

    // 회원 - 닉네임 변경
    @PostMapping("/members/profile/{nickname}")
    public ResponseEntity<MemberResponseDto> updateNickName(@PathVariable("nickname") String nickname, HttpServletRequest request) {
        return memberService.updateNickName(nickname, request);
    }

    // 회원 - 비밀번호 수정
    @PostMapping("/members/profile/password")
    public ResponseEntity<MemberResponseDto> updateMemberPwd(HttpServletRequest request, @RequestBody MemberPwdDto memberPwdDto) {
        return memberService.updateMemberPwd(request, memberPwdDto);
    }

}

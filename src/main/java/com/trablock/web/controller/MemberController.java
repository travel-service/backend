package com.trablock.web.controller;

import com.trablock.web.config.jwt.JwtTokenService;
import com.trablock.web.dto.member.MemberProfileDto;
import com.trablock.web.dto.member.MemberSaveDto;
import com.trablock.web.dto.member.MemberDto;
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
import java.util.Map;
import java.util.Optional;

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

    // 비회원 - 로그인
    @PostMapping("/api/login")
    public String login(@RequestBody LoginForm loginForm, HttpServletResponse response) {
        return memberServiceImpl.MemberLogin(loginForm, response);
    }

    // 회원 - 회원 개인페이지 필요 DATA + (여행 디렉토리도 추가 예정)
    @GetMapping("/api/user/my-page")
    public ResponseEntity<?> getMemberPage(HttpServletRequest request) {
        return memberServiceImpl.MemberPage(request);
    }

    // 회원 - 회원 개인페이지 프로필 사진
    @GetMapping("/api/user/my-page/img")
    public ResponseEntity<?> getMemberImg(HttpServletRequest request) throws FileNotFoundException {
        return memberServiceImpl.MemberImg(request);
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
//    @PutMapping("/api/user/profile/edit")


    // 회원 - 한 줄 소개 수정 - 사용보류
    @PutMapping("/api/user/profile/{bio}")
    public void updateComment(@PathVariable("bio") String bio, HttpServletRequest request) {
        memberServiceImpl.UpdateComment(bio, request);
    }

    // 회원
    @PutMapping("/api/user/profile/update")
    public void updateProfile(@RequestBody MemberDto memberUpdateDto, HttpServletRequest request, HttpServletResponse response) {
        return;
    }



}

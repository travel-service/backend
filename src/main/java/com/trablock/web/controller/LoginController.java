package com.trablock.web.controller;

import com.trablock.web.config.jwt.JwtTokenProvider;
import com.trablock.web.dto.MemberSaveDto;
import com.trablock.web.entity.member.*;
import com.trablock.web.repository.MemberRepository;
import com.trablock.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    
    @PostMapping("/signup")
    public Long signup(@RequestBody MemberSaveDto signupForm) {
        return memberService.MemberSignUp(signupForm);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginForm loginForm) {
        return memberService.MemberLogin(loginForm);
    }
}

package com.trablock.web.controller;

import com.trablock.web.config.jwt.JwtTokenProvider;
import com.trablock.web.entity.member.Gender;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.member.MemberInfo;
import com.trablock.web.entity.member.MemberProfile;
import com.trablock.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @PostMapping("/signup")
    public Long signup(@RequestBody Map<String, String> member) {
        return memberRepository.save(Member.builder()
                .userName(member.get("userName"))
                .password(passwordEncoder.encode(member.get("password")))
                .realName(member.get("realName"))
                .memberProfile(new MemberProfile(member.get("nickName"), null))
                .memberInfo(new MemberInfo(member.get("birthday"), Gender.valueOf(member.get("gender")), member.get("phoneNum"), member.get("email")))
                .roles(Collections.singletonList("ROLE_USER")) // 일반 유저
                .build()).getId();
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> loginForm) {
        Member member = memberRepository.findByUserName(loginForm.get("userName"))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));

        if (!passwordEncoder.matches(loginForm.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호");
        }
        return jwtTokenProvider.createToken(member.getUserName(), member.getRoles());
    }
}

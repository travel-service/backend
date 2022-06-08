package com.trablock.web.controller;

import com.trablock.web.dto.MemberSaveDto;
import com.trablock.web.entity.member.*;
import com.trablock.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/api/user/test")
    public String getInfo() {
        return "TOKEN OK";
    }

    @PostMapping("/api/signup")
    public Long signup(@RequestBody MemberSaveDto signupForm) {
        return memberService.MemberSignUp(signupForm);
    }

    @PostMapping("/api/login")
    public String login(@RequestBody LoginForm loginForm, HttpServletResponse response) {
        return memberService.MemberLogin(loginForm, response);
    }
}

package com.trablock.demo.controller;

import com.trablock.demo.dto.MemberSaveRequestDto;
import com.trablock.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller를 Home과 구분 지은건 무지성 나중에 합칠 예정
 */
@RequiredArgsConstructor
@Controller
public class LoginController {
    private final MemberService memberService;

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(MemberSaveRequestDto requestDto) {
        memberService.save(requestDto);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}

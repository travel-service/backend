package com.trablock.web.service;


import com.trablock.web.config.jwt.JwtTokenProvider;
import com.trablock.web.dto.MemberSaveDto;
import com.trablock.web.entity.member.*;
import com.trablock.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public Long MemberSignUp(MemberSaveDto memberSaveDto) {
        Optional<Member> valid = memberRepository.findByUserName(memberSaveDto.getUserName());

        if (valid.isEmpty()) {
            return memberRepository.save(Member.builder()
                            .userName(memberSaveDto.getUserName())
                            .password(passwordEncoder.encode(memberSaveDto.getPassword()))
                            .realName(memberSaveDto.getRealName())
                            .memberProfile(new MemberProfile(memberSaveDto.getNickName(), null))
                            .memberInfo(new MemberInfo(memberSaveDto.getBirthday(), Gender.valueOf(memberSaveDto.getGender()),
                                    memberSaveDto.getPhoneNum(), memberSaveDto.getEmail()))
                            .roles(Collections.singletonList("ROLE_USER")) // 일반 유저
                            .build()).getId();
        } else throw new IllegalArgumentException("중복 되는 아이디 존재");
    }

    public String MemberLogin(LoginForm loginForm) {
        Member member = memberRepository.findByUserName(loginForm.getUserName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!passwordEncoder.matches(loginForm.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
        else {
            return jwtTokenProvider.createToken(member.getUserName(), member.getRoles());
        }
    }
}

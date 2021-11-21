package com.trablock.demo.config;

import com.trablock.demo.domain.member.MemberContext;
import com.trablock.demo.service.member.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 로그인 시도, DB의 Password 와 일치, 불일치 판별
 * 아이디 중복은 검사 안함(회원가입 시 중복 차단)
 */

@RequiredArgsConstructor
@Component
public class FormAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailService customUserDetailService;
    private final BCryptPasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userId = authentication.getName();
        String password = (String) authentication.getCredentials();

        MemberContext memberContext = (MemberContext) customUserDetailService.loadUserByUsername(userId);
        String passwordFromDb = memberContext.getMember().getPassword();

        if(!encoder.matches(password, passwordFromDb)) {
            throw new BadCredentialsException("비밀번호가 틀립니다.");
        }

        return new UsernamePasswordAuthenticationToken(memberContext.getMember(), passwordFromDb, memberContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}

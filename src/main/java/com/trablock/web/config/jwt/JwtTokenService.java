package com.trablock.web.config.jwt;

import com.trablock.web.entity.member.Member;
import com.trablock.web.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtTokenService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    // AccessToken 으로 userName(아이디) 흭득
    public String tokenToUserName(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        return jwtTokenProvider.getUserName(accessToken);
    }

    // AccessToken 으로 userId(PK) 흭득
    public Long tokenToUserId(HttpServletRequest request) {
        String userName = tokenToUserName(request);
        return memberRepository.findByUserName(userName).orElseThrow().getId();
    }

    // AccessToken 으로 nickName 흭득
    public String tokenToNickName(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        Member member = memberRepository.findByUserName(jwtTokenProvider.getUserName(accessToken)).orElseThrow();
        return member.getMemberProfile().getNickName();
    }
}

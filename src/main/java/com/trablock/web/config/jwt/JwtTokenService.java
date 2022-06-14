package com.trablock.web.config.jwt;

import com.trablock.web.entity.member.Member;
import com.trablock.web.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class JwtTokenService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    // AccessToken 으로 userName(아이디) 흭득
    public String TokenToUserName(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        return jwtTokenProvider.getUserName(accessToken);
    }

    // AccessToken 으로 userId(PK) 흭득
    public Long TokenToUserId(HttpServletRequest request) {
        String userName = TokenToUserName(request);
        return memberRepository.findByUserName(userName).get().getId();
    }

    // AccessToken 으로 nickName 흭득
    public String TokenToNickName(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveRefreshToken(request);
        Member member = memberRepository.findByUserName(jwtTokenProvider.getUserName(accessToken)).get();
        return member.getMemberProfile().getNickName();
    }
}

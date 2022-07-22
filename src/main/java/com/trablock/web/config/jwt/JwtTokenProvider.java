package com.trablock.web.config.jwt;

import com.trablock.web.repository.member.MemberRepository;
import com.trablock.web.repository.member.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private String secretKey = "trablock";

    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final MemberRepository memberRepository;

    // AccessToken 유효시간 1m
    private long accessTokenValidTime = 60 * 60 * 1000L;

    // RefreshToken 유효시간 2m
    private long refreshTokenValidTime = 60 * 60 * 1000L;
    
    //secretKey 암호화
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createAccessToken(String userName, List<String> roles) {
        return this.createToken(userName, roles, accessTokenValidTime);
    }

    public String createRefreshToken(String userName, List<String> roles) {
        return this.createToken(userName, roles, refreshTokenValidTime);
    }

    public String createToken(String userName, List<String> roles, Long tokenValidTime) {
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put("roles", roles);
        Date now = new Date();
        
        return Jwts.builder()
                .setClaims(claims) // 정보
                .setIssuedAt(now) // 토큰 발행 시간
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // 토큰 만료시간
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, 키
                .compact();
    }

    // 토큰 인증 정보 조회
    public Authentication getAuthentication (String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserName(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원정보 추출
    public String getUserName(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    //Request Header 에서 AccessToken 값 추출
    public String resolveAccessToken(HttpServletRequest request) {
        if(request.getHeader("Authorization") != null)
            return request.getHeader("Authorization").substring(7);
        return null;
    }

    //Request Header 에서 RefreshToken 값 추출
    public String resolveRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // 토큰 validation
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
    }

    //AccessToken response
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("Authorization", "bearer " + accessToken);
    }

    //RefreshToken response
    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/"); //쿠키의 유효범위 추후 서비스 발전시 쿠키의 범위 설정 필요
        cookie.setMaxAge(7 * 24 * 60 * 60);

        response.addCookie(cookie);
    }

    //RefreshToken logout response
    public void setHeaderLogoutRefreshToken(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/"); //쿠키의 유효범위 추후 서비스 발전시 쿠키의 범위 설정 필요
        cookie.setMaxAge(60);

        response.addCookie(cookie);
    }
    //Token 존재?
    public boolean existsRefreshToken(String refreshToken) {
        return tokenRepository.existsByRefreshToken(refreshToken);
    }

    //권한 정보 가져오기
    public List<String> getRoles(String userName) {
        return memberRepository.findByUserName(userName).get().getRoles();
    }
}

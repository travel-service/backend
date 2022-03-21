package com.trablock.web.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private String secretKey = "trablock";
    private UserDetailsService userDetailsService;

    //토큰 유효시간
    private long tokenValidTime = 60 * 60 * 1000L;
    
    //secretKey 암호화
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
    
    public String createToken(String memberPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(memberPk);
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
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    //Request Header 에서 토큰 값 추출
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTHp-TOKEN");
    }

    // 토큰 validation
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    
}

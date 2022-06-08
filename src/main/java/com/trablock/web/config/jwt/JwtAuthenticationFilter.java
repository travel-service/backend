package com.trablock.web.config.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        //유효한 토큰인가?
        if (accessToken != null) {
            // AccessToken 이 유효하면?
            if (jwtTokenProvider.validateToken(accessToken)) {
                this.setAuthentication(accessToken);
            }
            // AccessToken 은 만료, RefreshToken 은 존재
            else if(!jwtTokenProvider.validateToken(accessToken) && refreshToken != null) {

                //RefreshToken 유효?
                boolean validRefreshToken = jwtTokenProvider.validateToken(refreshToken);

                //RefreshToken DB에 존재?
                boolean isRefreshToken = jwtTokenProvider.existsRefreshToken(refreshToken);

                //RefreshToken이 유효기간 남았고 DB에 남아있다면 AccessToken 새로 발급
                if (validRefreshToken && isRefreshToken) {
                    String userName = jwtTokenProvider.getUserName(refreshToken);
                    List<String> roles = jwtTokenProvider.getRoles(userName);
                    String newAccessToken = jwtTokenProvider.createAccessToken(userName, roles);
                    jwtTokenProvider.setHeaderAccessToken(response, newAccessToken);
                    this.setAuthentication(newAccessToken);
                }
            }
        }
        chain.doFilter(request, response);
    }

    public void setAuthentication(String token) {
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}

package com.trablock.web.repository.member;

import com.trablock.web.entity.auth.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<RefreshToken, Long> {
    boolean existsByRefreshToken(String token);
}

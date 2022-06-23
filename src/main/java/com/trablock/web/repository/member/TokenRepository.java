package com.trablock.web.repository.member;

import com.trablock.web.entity.auth.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<RefreshToken, Long> {
    boolean existsByRefreshToken(String token);

    @Query("delete from RefreshToken where refreshToken = :token")
    boolean deleteRefreshToken(String token);
}

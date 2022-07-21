package com.trablock.web.repository.member;

import com.trablock.web.entity.auth.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TokenRepository extends JpaRepository<RefreshToken, Long> {
    boolean existsByRefreshToken(String token);

    @Transactional
    @Query("select r.id from RefreshToken r where r.refreshToken = :token")
    Long findByRefreshToken(@Param("token")String token);
}

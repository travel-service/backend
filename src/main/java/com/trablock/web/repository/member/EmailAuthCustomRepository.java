package com.trablock.web.repository.member;

import com.trablock.web.entity.member.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailAuthCustomRepository {
    Optional<EmailAuth> findValidAuthByEmail(String email, String uuid, LocalDateTime currentTime);
}

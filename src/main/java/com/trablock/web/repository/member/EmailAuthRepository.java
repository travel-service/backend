package com.trablock.web.repository.member;

import com.trablock.web.entity.member.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long>, EmailAuthCustomRepository {
}

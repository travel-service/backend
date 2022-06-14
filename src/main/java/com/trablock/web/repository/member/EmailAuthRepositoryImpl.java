package com.trablock.web.repository.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.trablock.web.entity.member.EmailAuth;
import com.trablock.web.entity.member.QEmailAuth;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;

public class EmailAuthRepositoryImpl implements EmailAuthCustomRepository {

    JPAQueryFactory jpaQueryFactory;

    public EmailAuthRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    public Optional<EmailAuth> findValidAuthByEmail(String email, String uuid, LocalDateTime currentTime) {
        EmailAuth emailAuth = jpaQueryFactory
                .selectFrom(QEmailAuth.emailAuth)
                .where(QEmailAuth.emailAuth.email.eq(email),
                        QEmailAuth.emailAuth.uuid.eq(uuid),
                        QEmailAuth.emailAuth.expireDate.goe(currentTime),
                        QEmailAuth.emailAuth.expired.eq(false))
                .fetchFirst();

        return Optional.ofNullable(emailAuth);
    }
}

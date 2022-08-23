package com.trablock.web.entity.member;

import com.trablock.web.dto.member.MemberSaveDto;
import lombok.*;
import org.springframework.scheduling.annotation.Async;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailAuth {
    private static final Long MAX_EXPIRE_TIME = 5L; // 이메일 인증 유효시간

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;
    private String uuid;
    private Boolean expired;
    private LocalDateTime expireDate;

    @Builder
    public EmailAuth(String email) {
        this.email = email;
        this.uuid= UUID.randomUUID().toString();
        this.expired = false;
        this.expireDate = LocalDateTime.now().plusMinutes(MAX_EXPIRE_TIME);
    }

    // 인증 했으면 True
    public void useToken() {
        this.expired = true;
    }

}

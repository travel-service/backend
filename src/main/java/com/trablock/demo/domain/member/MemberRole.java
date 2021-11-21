package com.trablock.demo.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 모든 Member의 default 권한은 USER, 아직은 수동으로 ADMIN 부여
 */
@Getter
@RequiredArgsConstructor
public enum MemberRole {
    USER("ROLE_USER", "일반사용자"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
}

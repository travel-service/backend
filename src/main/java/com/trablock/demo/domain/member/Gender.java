package com.trablock.demo.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** default 값을 위한 UNKNOWN 추가*/
@Getter
@RequiredArgsConstructor
public enum Gender {
    MALE, FEMALE, UNKNOWN
}

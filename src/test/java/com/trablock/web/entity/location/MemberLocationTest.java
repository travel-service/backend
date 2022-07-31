package com.trablock.web.entity.location;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MemberLocationTest {

    @Test
    void create() throws Exception {
        //given
        MemberLocation memberLocation;

        //when
        memberLocation = MemberLocation.builder()
                .memberId(1L)
                .locationId(1L)
                .isPublic(true)
                .build();

        //then
        assertThat(memberLocation.getMemberId()).isEqualTo(1L);
        assertThat(memberLocation.getLocationId()).isEqualTo(1L);
        assertTrue(memberLocation.isPublic());
    }

}
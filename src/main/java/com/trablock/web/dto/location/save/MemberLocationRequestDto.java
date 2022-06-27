package com.trablock.web.dto.location.save;

import com.trablock.web.entity.location.MemberLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

@AllArgsConstructor
public class MemberLocationRequestDto {

    private Boolean isMember;
    private Long memberId;
    private Boolean isPublic;

    public MemberLocation toEntity(Long locationId) {

        return MemberLocation.builder()
                .memberId(memberId)
                .locationId(locationId)
                .isPublic(isPublic)
                .build();
    }
}

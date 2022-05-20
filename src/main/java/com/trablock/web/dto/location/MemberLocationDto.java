package com.trablock.web.dto.location;

import com.trablock.web.entity.location.MemberLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class MemberLocationDto {

    private Long memberId;
    private Long locationId;
    private boolean isPublic;

    public MemberLocation toEntity() {
        return MemberLocation.builder()
                .memberId(memberId)
                .locationId(locationId)
                .isPublic(isPublic)
                .build();
    }
}

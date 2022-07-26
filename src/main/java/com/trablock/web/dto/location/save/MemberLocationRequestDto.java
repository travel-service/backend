package com.trablock.web.dto.location.save;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trablock.web.entity.location.MemberLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MemberLocationRequestDto {

    @JsonProperty("memberId")
    private Long memberId;

    @JsonProperty("isPublic")
    private Boolean isPublic;

    public MemberLocation toEntity(Long locationId) {
        return MemberLocation.builder()
                .memberId(memberId)
                .locationId(locationId)
                .isPublic(isPublic)
                .build();
    }
}

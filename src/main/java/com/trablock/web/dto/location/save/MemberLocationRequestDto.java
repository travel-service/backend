package com.trablock.web.dto.location.save;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trablock.web.entity.location.MemberLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@AllArgsConstructor
public class MemberLocationRequestDto {

    @JsonProperty("memberId")
    private Long memberId;

    @JsonProperty("isPublic")
    private Boolean isPublic;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public MemberLocation toEntity(Long locationId) {
        return MemberLocation.builder()
                .memberId(memberId)
                .locationId(locationId)
                .isPublic(isPublic)
                .build();
    }
}

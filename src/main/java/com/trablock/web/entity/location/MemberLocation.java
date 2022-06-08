package com.trablock.web.entity.location;

import com.trablock.web.dto.location.MemberLocationDto;
import com.trablock.web.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class MemberLocation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @JoinColumn(name = "MEMBER_ID")
    private Long memberId;

    @JoinColumn(name = "LOCATION_ID")
    private Long locationId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private boolean isPublic;

    public MemberLocationDto toDto() {
        return MemberLocationDto.builder()
                .memberId(member.getId())
                .locationId(locationId)
                .isPublic(isPublic)
                .build();
    }

}

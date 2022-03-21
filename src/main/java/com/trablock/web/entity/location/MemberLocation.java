package com.trablock.web.entity.location;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class MemberLocation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @JoinColumn(name = "MEMBER_ID")
    private Long memberId;

    @JoinColumn(name = "LOCATION_ID")
    private Long locationId;

    private boolean isPrivate;

    public MemberLocation(Long memberId, Long locationId, boolean isPrivate) {
        this.memberId = memberId;
        this.locationId = locationId;
        this.isPrivate = isPrivate;
    }
}

package com.trablock.web.entity.location;

import com.trablock.web.domain.Address;
import com.trablock.web.domain.LocationType;
import com.trablock.web.entity.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class MemberLocation {

    @Id
    @Column(name = "mebmerloc_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String locationName;

    @Enumerated(STRING)
    private LocationType locationType;

    @Embedded
    private Address address;
}

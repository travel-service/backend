package com.trablock.demo.domain.member;

import lombok.Getter;

import javax.persistence.*;

/** MemberLocation 은 회의 이후 구현 */
@Entity
@Getter
public class MemberLocation {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String address;
    private byte[] locationImg;

    @Enumerated(EnumType.STRING)
    private LocationType locationType;
}

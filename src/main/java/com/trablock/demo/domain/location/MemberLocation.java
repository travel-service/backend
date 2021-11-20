package com.trablock.demo.domain.location;

import com.trablock.demo.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@Entity
@NoArgsConstructor
public class MemberLocation implements Location {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String name;
    private String address1;
    private String address2;

    @Embedded
    private Coords coords;

    private byte[] locationImg;

    @Embedded
    private Information information;

    private LocationType locationType;

    @Builder
    public MemberLocation(Member member, String name, String address1, String address2, Coords coords, byte[] locationImg, Information information, LocationType locationType) {
        this.member = member;
        this.name = name;
        this.address1 = address1;
        this.address2 = address2;
        this.coords = coords;
        this.locationImg = locationImg;
        this.information = information;
        this.locationType = locationType;
    }

}

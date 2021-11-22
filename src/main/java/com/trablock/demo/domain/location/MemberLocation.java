package com.trablock.demo.domain.location;

import com.trablock.demo.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;


@Entity
@Table(name = "member_location")
@NoArgsConstructor
@Getter @Builder
public class MemberLocation extends Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String address1;
    private String address2;

    @Embedded
    private Coords coords;

    private byte[] locationImg;

    @Embedded
    private Information information;

    private LocationType locationType;
}

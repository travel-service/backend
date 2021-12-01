package com.trablock.demo.domain.location;

import com.trablock.demo.domain.member.Member;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;


@Entity(name = "member_location")
@NoArgsConstructor(access = PROTECTED)
@Access(AccessType.FIELD)
@SuperBuilder @Getter
public class MemberLocation extends Location {

//    @Id
//    @GeneratedValue(strategy = IDENTITY)
//    @Column(name="member_location_id")
//    private Long id;

    @ManyToOne
    private Member member;
}

package com.trablock.demo.domain.location;

import com.trablock.demo.domain.member.Member;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static lombok.AccessLevel.*;


@Entity
@DiscriminatorValue("MemberLocation")
@NoArgsConstructor(access = PROTECTED)
@Getter
@SuperBuilder
@Table(name = "member_location")
public class MemberLocation extends Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

}

package com.trablock.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String username;
    private String password;

    @Embedded
    private Profile profile;

    @Embedded
    private PersonInfo personInfo;

    @Enumerated(EnumType.STRING)
    private MemberRole role; // default : USER

    @OneToMany(mappedBy = "member")
    private List<MemberLocation> memberLocations = new ArrayList<>();
}

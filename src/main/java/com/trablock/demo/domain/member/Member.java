package com.trablock.demo.domain.member;

import com.trablock.demo.domain.location.MemberLocation;
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
    @Column(name = "member_id")
    private Long id;

    @Column(length = 20, nullable = false)
    private String userId;

    @Column(nullable = false) // 길이제한 없는 이유는 난수화 떄문에 프론트가 처리해야합니다.
    private String password;

    @Column(nullable = false)
    private String username;

    @Embedded
    private Profile profile;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id") /** 1:1 관계, PersonInfo PK == Member Pk라 판단, 그래서 이렇게 매핑, 의견 있으면 말해줘요 */
    private PersonInfo personInfo;

    @Enumerated(EnumType.STRING)
    private MemberRole role; // default : USER

    @OneToMany(mappedBy = "member")
    private List<MemberLocation> memberLocations = new ArrayList<>();
}

package com.trablock.demo.domain;

import lombok.*;

import javax.persistence.*;

/**
 * OneToOne 의 정확한 구현을 이해 못해서 profile, personinfo 등 구현 못함
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String username;
    private String password;
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id") /** 아직 구현 제대로 못해서 작동 안합니다 */
    private Profile profile;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

}

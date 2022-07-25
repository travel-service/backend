package com.trablock.web.entity.member;


import com.trablock.web.dto.member.MemberSaveDto;
import com.trablock.web.entity.location.MemberLocation;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.UserDirectory;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Builder(builderMethodName = "MemberBuilder")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements UserDetails{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id; //회원 번호

    private String userName; //아이디
    private String password;

    private Boolean emailAuth; // 이메일 인증 여부

    @Embedded
    private MemberProfile memberProfile;

    @Embedded
    private MemberInfo memberInfo;

    @ElementCollection(fetch = EAGER)
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberLocation> memberLocation = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Plan> plans = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<UserDirectory> userDirectories = new ArrayList<>();

    @Builder
    public static MemberBuilder builder(MemberSaveDto memberSaveDto, String pwd) {
        return MemberBuilder()
                .userName(memberSaveDto.getUserName())
                .password(pwd)
                .emailAuth(false)
                .memberProfile(new MemberProfile(memberSaveDto.getNickName(), null))
                .memberInfo(new MemberInfo(memberSaveDto.getBirthday(), Gender.valueOf(memberSaveDto.getGender()),
                        memberSaveDto.getEmail()))
                .roles(Collections.singletonList("ROLE_USER")); // 일반 유저
    }

    public void emailVerifiedSuccess() {
        this.emailAuth = true;
    }


    /**
     * 하위 로직은 TOKEN 기반 로그인을 위해 필요
     * Entity에 존재해야 하는지도 의문이고 없애고 싶고 없앨 수 있을거 같음
     * 근데 로직을 겉할기만 해서 아직은 못합니다.
    * */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}

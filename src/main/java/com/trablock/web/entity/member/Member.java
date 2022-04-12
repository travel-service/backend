package com.trablock.web.entity.member;


import com.trablock.web.entity.location.MemberLocation;
import com.trablock.web.entity.plan.Plan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member implements UserDetails{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id; //회원 번호

    private String userName; //아이디

    private String password;

    private String realName; //회원 이름

    @Embedded
    private MemberProfile memberProfile;

    @Embedded
    private MemberInfo memberInfo;

    @ElementCollection(fetch = EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberLocation> memberLocation = new ArrayList<>();

    //임시, 추후 수정
    @OneToMany(mappedBy = "member")
    private List<Plan> plan = new ArrayList<>();

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

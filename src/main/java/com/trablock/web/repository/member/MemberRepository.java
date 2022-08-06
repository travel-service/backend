package com.trablock.web.repository.member;

import com.trablock.web.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserName(String username);

    boolean existsByUserName(String username);

    @Query("select (count(m) > 0) from Member m where m.memberProfile.nickName= :nickName")
    boolean existsByNickName(@Param("nickName") String nickName);

    @Query("select m from Member as m where m.id= :id")
    Optional<Member> findMemberId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("update Member as m set m.password = :pwd where m.userName= :userName")
    int updateMemberPwd(@Param("pwd") String pwd, @Param("userName") String userName);

    @Query("select m from Member m where m.memberInfo.email= :email")
    Optional<Member> findByEmail(@Param("email") String email);
}

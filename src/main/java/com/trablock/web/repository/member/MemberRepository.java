package com.trablock.web.repository.member;

import com.trablock.web.dto.member.MemberProfileDto;
import com.trablock.web.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserName(String username);

    @Query("select m from Member as m where m.id= :id")
    Member findMemberId(@Param("id") Long id);

    // 쿼리가 왜이래요? => 다른 정보들은 보안적인 측면에서 위험할거 같아요
    @Query("select m.memberProfile.nickName from Member as m where m.memberProfile.nickName= :nickName")
    String findByNickName(@Param("nickName") String nickName);

    @Modifying
    @Transactional
    @Query("update Member as m set m.password = :pwd where m.userName= :userName")
    void updateMemberPwd(@Param("pwd") String pwd, @Param("userName") String userName);

    @Query("select m from Member m where m.memberInfo.email= :email")
    Optional<Member> findByEmail(String email);
}

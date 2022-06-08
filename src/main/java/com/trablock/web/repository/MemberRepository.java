package com.trablock.web.repository;

import com.trablock.web.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserName(String username);

    @Query("select m from Member as m where m.id= :id")
    Member findMemberId(Long id);

}

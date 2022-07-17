package com.trablock.web.repository.plan;

import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.UserDirectory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserDirectoryRepository extends JpaRepository<UserDirectory, Long> {

    @Query("select u from UserDirectory u where u.id = :userDirectoryId and u.member.id = :memberId")
    UserDirectory findUserDirectoryById(@Param("userDirectoryId") Long userDirectoryId, @Param("memberId") Long memberId);

    @Query("select u from UserDirectory u where u.member= :memberId")
    List<UserDirectory> findMemberIdForList(@Param("memberId") Optional<Member> memberId);

    @Query("select u from UserDirectory u where u.member= :memberId")
    List<UserDirectory> findUserDirectoryById(@Param("memberId") Member memberId);

    @Query("select count(u) from UserDirectory as u where u.member = :memberId")
    int findMemberIdForCount(@Param("memberId") Member id);

    @Query("select u from UserDirectory u where u.id = :id")
    UserDirectory findByIdForUserDirectory(@Param("id") Long id);
}

package com.trablock.web.repository.plan;

import com.trablock.web.entity.plan.UserDirectory;
import com.trablock.web.entity.plan.enumtype.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDirectoryRepository extends JpaRepository<UserDirectory, Long> {

    // TODO TEST
    @Query("select u from UserDirectory u where u.id = :userDirectoryId and u.member.id = :memberId")
    UserDirectory findUserDirectoryById(@Param("userDirectoryId") Long userDirectoryId, @Param("memberId") Long memberId);

    // TODO TEST
    @Query("select u from UserDirectory u where u.member.id = :memberId and u.status = :status")
    List<UserDirectory> findMemberIdForList(@Param("memberId") Long memberId, @Param("status") Status status);

    // TODO TEST
    @Query("select u from UserDirectory u where u.member.id = :memberId")
    List<UserDirectory> findUserDirectoryById(@Param("memberId") Long memberId);

    // TODO TEST
    @Query("select count(u) from UserDirectory as u where u.member.id = :memberId")
    int findMemberIdForCount(@Param("memberId") Long id);

    // TODO TEST
    @Query("select u from UserDirectory u where u.id = :id")
    UserDirectory findByIdForUserDirectory(@Param("id") Long id);

    // TODO TEST
    @Query("select count(u) from UserDirectory u where u.status = :status")
    int findUserDirectoryByStatus(@Param("status") Status status);

}
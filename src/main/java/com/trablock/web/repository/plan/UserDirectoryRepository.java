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

    UserDirectory findUserDirectoryById(Long id);

    @Query("select u from UserDirectory u where u.member= :id")
    List<UserDirectory> findMemberIdForList(@Param("id") Optional<Member> id);

    @Query("select count(u) from UserDirectory as u where u.member = :id")
    int findMemberIdForCount(@Param("id") Member id);

    @Query("select u from UserDirectory u where u.id = :id")
    UserDirectory findByIdForUserDirectory(@Param("id") Long id);

}

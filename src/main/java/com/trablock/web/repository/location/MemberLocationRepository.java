package com.trablock.web.repository.location;

import com.trablock.web.entity.location.MemberLocation;
import com.trablock.web.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberLocationRepository extends JpaRepository<MemberLocation, Long> {

    @Query("select m from MemberLocation m where m.member = :member")
    List<MemberLocation> findAllByMember(@Param("member") Member member);

    @Query("select m from MemberLocation m where m.memberId = :memberId")
    List<MemberLocation> findAllByMemberId(@Param("memberId") Long memberId);

    @Query("select m from MemberLocation m where m.locationId = :locationId")
    Optional<MemberLocation> findByLocationId(@Param("locationId") Long locationId);

    @Query("select m from MemberLocation m where m.memberId = :memberId and m.isPublic = true")
    List<MemberLocation> findAllByMemberIdAndIsPublicTrue(@Param("memberId") Long memberId);

    @Query("select m from MemberLocation m where m.member = :member and m.isPublic = true")
    List<MemberLocation> findAllByMemberAndIsPublicTrue(@Param("member") Member member);

    @Query("select (count(m) > 0) from MemberLocation m where m.locationId = :locationId")
    boolean existsMemberLocationByLocationId(@Param("locationId") Long locationId);

    /**
     * https://www.baeldung.com/spring-data-partial-update
     *
     * @return
     */
    @Modifying
    @Query("update MemberLocation m set m.isPublic = false where m.locationId = :locationId ")
    boolean updateMemberLocationBeingPrivate(@Param("locationId") Long locationId);

    @Modifying
    @Query("update MemberLocation m set m.isPublic = true where m.locationId = :locationId ")
    void updateMemberLocationBeingPublic(@Param("locationId") Long locationId);

    @Modifying
    @Query("delete from MemberLocation m where m.locationId = :locationId")
    void deleteMemberLocationByLocationId(@Param("locationId") Long locationId);

}

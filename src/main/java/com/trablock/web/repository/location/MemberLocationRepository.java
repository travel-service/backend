package com.trablock.web.repository.location;

import com.trablock.web.entity.location.MemberLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberLocationRepository extends JpaRepository<MemberLocation, Long> {

    @Query("select m from MemberLocation m where m.memberId = :memberId")
    List<MemberLocation> findAllByMemberId(@Param("memberId") Long memberId);

    @Query("select m from MemberLocation m where m.locationId = :locationId")
    Optional<MemberLocation> findByLocationId(@Param("locationId") Long locationId);

    @Query("select m from MemberLocation m where m.memberId = :memberId and m.isPublic = true")
    List<MemberLocation> findAllByMemberIdAndIsPublicTrue(@Param("memberId") Long memberId);

    @Modifying
    @Query("delete from MemberLocation m where m.locationId = :locationId")
    void deleteMemberLocationByLocationId(@Param("locationId") Long locationId);

}

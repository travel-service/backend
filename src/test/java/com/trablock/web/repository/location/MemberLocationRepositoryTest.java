package com.trablock.web.repository.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.entity.location.Coords;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.location.MemberLocation;
import com.trablock.web.entity.member.Member;
import com.trablock.web.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.*;

@SpringBootTest
@Transactional
class MemberLocationRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    MemberLocationRepository memberLocationRepository;

    @PersistenceContext
    EntityManager em;


    /**
     * fixture
     */
    Location savedLoc;
    Location savedLoc2;
    Location savedLoc3;
    Member savedMember;

    @BeforeEach
    void init() {
        Member member = Member.builder()
                .userName("name")
                .password("1234")
                .build();

        savedMember = memberRepository.save(member);

        Location loc = Location.builder()
                .name("test")
                .areaCode(123)
                .address1("경기도 수원시 팔달구")
                .address2("권광로180번길 53-26")
                .coords(Coords.builder()
                        .latitude(37.123).longitude(127.123).build())
                .image("url/url")
                .type(LocationType.LODGE)
                .build();

        Location loc2 = Location.builder()
                .name("test2")
                .areaCode(123)
                .address1("경기도 수원시 권선구")
                .address2("권선동 1059-3")
                .coords(Coords.builder()
                        .latitude(37.123).longitude(127.123).build())
                .image("url/url")
                .type(LocationType.LODGE)
                .build();

        Location loc3 = Location.builder()
                .name("test3")
                .areaCode(123)
                .address1("경기도 수원시 팔달구")
                .address2("인계동")
                .coords(Coords.builder()
                        .latitude(37.123).longitude(127.123).build())
                .image("url/url")
                .type(LocationType.LODGE)
                .build();

        savedLoc = locationRepository.save(loc);
        savedLoc2 = locationRepository.save(loc2);
        savedLoc3 = locationRepository.save(loc3);
    }

    @Test
    void create() throws Exception {
        //given
        Long savedLocId = savedLoc.getId();

        MemberLocation memberLocation = MemberLocation.builder()
                .locationId(savedLocId)
                .memberId(savedLocId)
                .isPublic(true)
                .build();

        //when
        memberLocationRepository.save(memberLocation);

        em.flush();
        em.clear();

        Optional<MemberLocation> byLocationId = memberLocationRepository.findByLocationId(savedLocId);
        assumingThat(byLocationId.isPresent(), () -> {
            MemberLocation target = byLocationId.get();

            //then
            assertThat(target.getId()).isEqualTo(memberLocation.getId());
            assertThat(target.getLocationId()).isEqualTo(memberLocation.getLocationId());

            // 프록시를 꺼냈으므로 객체는 다름. Id는 같다.
            assertThat(target.getMemberId()).isEqualTo(memberLocation.getMemberId());

            System.out.println("target = " + target);
        });
    }

    @Test
    void read() throws Exception {
        //given
        Long savedMemberId = savedMember.getId();
        Long savedLocId = savedLoc.getId();
        Long savedLocId2 = savedLoc2.getId();
        Long savedLocId3 = savedLoc3.getId();

        MemberLocation memberLocation = MemberLocation.builder()
                .locationId(savedLocId)
                .memberId(savedLocId)
                .isPublic(true)
                .build();
        MemberLocation memberLocation2 = MemberLocation.builder()
                .locationId(savedLocId2)
                .memberId(savedLocId)
                .isPublic(true)
                .build();
        MemberLocation memberLocation3 = MemberLocation.builder()
                .locationId(savedLocId3)
                .memberId(savedLocId)
                .isPublic(true)
                .build();


        memberLocationRepository.save(memberLocation);
        memberLocationRepository.save(memberLocation2);
        memberLocationRepository.save(memberLocation3);

        em.flush();
        em.clear();

        //when
        List<MemberLocation> allByMember = memberLocationRepository.findAllByMember(savedMember);
        List<MemberLocation> allByMemberAndIsPublicTrue = memberLocationRepository.findAllByMemberAndIsPublicTrue(savedMember);

        //then
        for (MemberLocation location : allByMember) {
            assertThat(location.getMemberId()).isEqualTo(savedMember.getId());
            assertThat(location.isPublic()).isEqualTo(true);
        }

        for (MemberLocation location : allByMemberAndIsPublicTrue) {
            assertThat(location.getMemberId()).isEqualTo(savedMember.getId());
            assertThat(location.isPublic()).isEqualTo(true);
        }
    }

    @Test
    void update() throws Exception {
        //given
        Long savedMemberId = savedMember.getId();
        Long savedLocId = savedLoc.getId();
        Long savedLocId2 = savedLoc2.getId();
        Long savedLocId3 = savedLoc3.getId();

        MemberLocation memberLocation = MemberLocation.builder()
                .locationId(savedLocId)
                .memberId(savedLocId)
                .isPublic(true)
                .build();
        MemberLocation memberLocation2 = MemberLocation.builder()
                .locationId(savedLocId2)
                .memberId(savedLocId)
                .isPublic(true)
                .build();
        MemberLocation memberLocation3 = MemberLocation.builder()
                .locationId(savedLocId3)
                .memberId(savedLocId)
                .isPublic(true)
                .build();

        memberLocationRepository.save(memberLocation);
        memberLocationRepository.save(memberLocation2);
        memberLocationRepository.save(memberLocation3);

        em.flush();
        em.clear();

        List<MemberLocation> allByMember = memberLocationRepository.findAllByMember(savedMember);
        assumeTrue(allByMember.size() == 3);

        //when
        memberLocationRepository.updateMemberLocationBeingPrivate(savedLocId);

        em.flush();
        em.clear();

        List<MemberLocation> allByMemberAndIsPublicTrue = memberLocationRepository.findAllByMemberAndIsPublicTrue(savedMember);
        Optional<MemberLocation> target = memberLocationRepository.findByLocationId(savedLocId);
        //then
        assertThat(target.get().isPublic()).isEqualTo(false);
        assertThat(allByMemberAndIsPublicTrue.size()).isEqualTo(2);
        memberLocationRepository.updateMemberLocationBeingPublic(savedLocId);
        List<MemberLocation> allByMemberAndIsPublicTrue2 = memberLocationRepository.findAllByMemberAndIsPublicTrue(savedMember);
        assertThat(allByMemberAndIsPublicTrue2.size()).isEqualTo(3);

    }

    @Test
    void delete() throws Exception {
        //given
        Long savedMemberId = savedMember.getId();
        Long savedLocId = savedLoc.getId();
        Long savedLocId2 = savedLoc2.getId();
        Long savedLocId3 = savedLoc3.getId();

        MemberLocation memberLocation = MemberLocation.builder()
                .locationId(savedLocId)
                .memberId(savedLocId)
                .isPublic(true)
                .build();
        MemberLocation memberLocation2 = MemberLocation.builder()
                .locationId(savedLocId2)
                .memberId(savedLocId)
                .isPublic(true)
                .build();
        MemberLocation memberLocation3 = MemberLocation.builder()
                .locationId(savedLocId3)
                .memberId(savedLocId)
                .isPublic(true)
                .build();


        memberLocationRepository.save(memberLocation);
        memberLocationRepository.save(memberLocation2);
        memberLocationRepository.save(memberLocation3);

        em.flush();
        em.clear();

        // when
        memberLocationRepository.deleteMemberLocationByLocationId(savedLocId);
        List<MemberLocation> allByMember = memberLocationRepository.findAllByMember(savedMember);

        // then
        assertThat(allByMember.size()).isEqualTo(2);
    }

}
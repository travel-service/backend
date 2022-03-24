package com.trablock.web.repository.location;

import com.trablock.web.entity.location.Coords;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.location.MemberLocation;
import com.trablock.web.entity.location.type.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberLocationRepositoryTest {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    MemberLocationRepository memberLocationRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    void create() throws Exception {
        //given
        Location loc = Location.builder()
                .name("test")
                .address1("경기도 수원시 팔달구")
                .address2("권광로180번길 53-26")
                .coords(Coords.builder()
                        .latitude("37.123").longitude("127.123").build())
                .build();

        locationRepository.save(loc);

        Long locId = loc.getId();
        Long memberId = 1L;

        MemberLocation memLoc = new MemberLocation(memberId, locId, true);

        //when
        MemberLocation savedMemLoc = memberLocationRepository.save(memLoc);
        MemberLocation findMemLoc = memberLocationRepository.findByLocationId(locId).get();

        //then
        assertThat(findMemLoc).isEqualTo(savedMemLoc);
    }

    @Test
    void read() throws Exception {
        //given
        Location loc1 = Location.builder()
                .name("test")
                .address1("경기도 수원시 팔달구")
                .address2("권광로180번길 53-26")
                .coords(Coords.builder()
                        .latitude("37.123").longitude("127.123").build())
                .build();

        locationRepository.save(loc1);

        Location loc2 = Location.builder()
                .name("test")
                .address1("경기도 수원시 팔달구")
                .address2("권광로180번길 53-26")
                .coords(Coords.builder()
                        .latitude("37.123").longitude("127.123").build())
                .build();

        locationRepository.save(loc2);


        Long locId1 = loc1.getId();
        Long locId2 = loc2.getId();
        Long memberId = 1L;

        MemberLocation memLoc1 = new MemberLocation(memberId, locId1, true);
        MemberLocation memLoc2 = new MemberLocation(memberId, locId2, false);

        MemberLocation save1 = memberLocationRepository.save(memLoc1);
        MemberLocation save2 = memberLocationRepository.save(memLoc2);

        //when
        MemberLocation find1 = memberLocationRepository.findByLocationId(locId1).get();
        MemberLocation find2 = memberLocationRepository.findByLocationId(locId2).get();
        List<MemberLocation> allByMemberId = memberLocationRepository.findAllByMemberId(memberId);
        List<MemberLocation> allByMemberIdAndIsPublicTrue = memberLocationRepository.findAllByMemberIdAndIsPublicTrue(memberId);

        //then
        assertThat(find1).isEqualTo(save1);
        assertThat(find2).isEqualTo(save2);
        assertThat(allByMemberId.size()).isEqualTo(2);
        assertThat(allByMemberIdAndIsPublicTrue.size()).isEqualTo(1);
    }

    @Test
    void delete() throws Exception {
        //given
        Location loc1 = Location.builder()
                .name("test")
                .address1("경기도 수원시 팔달구")
                .address2("권광로180번길 53-26")
                .coords(Coords.builder()
                        .latitude("37.123").longitude("127.123").build())
                .build();

        locationRepository.save(loc1);

        Location loc2 = Location.builder()
                .name("test")
                .address1("경기도 수원시 팔달구")
                .address2("권광로180번길 53-26")
                .coords(Coords.builder()
                        .latitude("37.123").longitude("127.123").build())
                .build();

        locationRepository.save(loc2);

        Long locId1 = loc1.getId();
        Long locId2 = loc2.getId();
        Long memberId = 1L;

        MemberLocation memLoc1 = new MemberLocation(memberId, locId1, true);
        MemberLocation memLoc2 = new MemberLocation(memberId, locId2, false);

        memberLocationRepository.save(memLoc1);
        memberLocationRepository.save(memLoc2);

        Optional<MemberLocation> find1 = memberLocationRepository.findByLocationId(locId1);
        Optional<MemberLocation> find2 = memberLocationRepository.findByLocationId(locId2);

        //when

        find1.ifPresent(selectMLoc -> {
            memberLocationRepository.deleteMemberLocationByLocationId(locId1);
        });

        List<MemberLocation> allByMemberId = memberLocationRepository.findAllByMemberId(memberId);

        //then
        assertThat(allByMemberId.size()).isEqualTo(1);

    }

}
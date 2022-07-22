package com.trablock.web.repository.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.entity.location.Coords;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.location.MemberLocation;
import com.trablock.web.entity.member.Member;
import com.trablock.web.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class LocationRepositoryCustomTest {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    MemberLocationRepository memberLocationRepository;

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;


    @Test
    @DisplayName("memberId로 Location 조회")
    void findLocationsByMemberId() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .userName("name")
                .password("1234")
                .build());

        Location location = locationRepository.save(Location.builder()
                .name("test1")
                .areaCode(123)
                .address1("경기도 수원시 팔달구")
                .address2("권광로180번길 53-26")
                .image("url:GBSG/1234")
                .coords(Coords.builder()
                        .latitude(37.123).longitude(127.123).build()
                )
                .isMember(true)
                .type(LocationType.LODGE)
                .build());

        assertThat(location.getId()).isNotNull();

        MemberLocation memberLocation = memberLocationRepository.save(MemberLocation.builder()
                .locationId(location.getId())
                .member(member)
                .memberId(member.getId())
                .isPublic(true)
                .build());

        assertThat(memberLocation.getId()).isNotNull();

        Location location2 = locationRepository.save(Location.builder()
                .name("test2")
                .areaCode(123)
                .address1("경기도 수원시 권선구")
                .address2("권선대로 694")
                .image("url:GBSG/1244")
                .coords(Coords.builder()
                        .latitude(37.153).longitude(127.123).build()
                )
                .isMember(true)
                .type(LocationType.RESTAURANT)
                .build());

        assertThat(location2.getId()).isNotNull();

        MemberLocation memberLocation2 = memberLocationRepository.save(MemberLocation.builder()
                .locationId(location.getId())
                .member(member)
                .memberId(member.getId())
                .isPublic(true)
                .build());

        assertThat(memberLocation2.getId()).isNotNull();

        //when
        List<Location> locationsByMemberId = locationRepository.findLocationsByMemberId(member.getId());

        em.flush();

        //then
        assertThat(locationsByMemberId.isEmpty()).isFalse();
        locationsByMemberId.forEach(locInList -> assertThat(locInList.getIsMember()).isTrue());
        assertThat(locationsByMemberId.size()).isEqualTo(2);
    }
}
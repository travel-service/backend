package com.trablock.web.entity.plan;

import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.member.Member;
import com.trablock.web.repository.location.LocationRepository;
import com.trablock.web.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional(readOnly = true)
@Rollback(value = false)
class SelectedLocationTest {

    @PersistenceContext
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    LocationRepository locationRepository;

    @Test
    public void selectedLocationEntityTest() throws Exception {
        //given
//        Member member = new Member("member");
//        memberRepository.save(member);
//
//        Location location1 = Location.builder().build();
//        locationRepository.save(location1);
//
//        SelectedLocation selectedLocation = SelectedLocation.builder()
//                .member(member)
//                .location(location1)
//                .selectStatus(SelectStatus.SELECTED)
//                .build();
//
//        selectedLocationRepository.save(selectedLocation);
//
//        em.flush();
//        em.clear();

//        //when
//        List<SelectedLocation> all = selectedLocationRepository.findAll();
//
//        //then
//        assertThat(all.size()).isEqualTo(1);
     }
}
package com.trablock.web.entity.plan;

import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.member.Member;
import com.trablock.web.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
@Rollback(value = false)
class DayTest {

    @PersistenceContext
    EntityManager em;
    @Autowired PlanRepository planRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired DayRepository dayRepository;
    @Autowired SelectedLocationRepository selectedLocationRepository;
    @Autowired MainDirectoryRepository mainDirectoryRepository;
    @Autowired LocationRepository locationRepository;

    @Test
    public void dayEntityTest() throws Exception {
        Location location1 = new Location();
        Location location2 = new Location();
        Location location3 = new Location();
        Location location4 = new Location();
        locationRepository.save(location1);
        locationRepository.save(location2);
        locationRepository.save(location3);
        locationRepository.save(location4);

        em.flush();
        em.clear();

        Member member1 = new Member("member1");
        memberRepository.save(member1);

        MainDirectory mainDirectory = new MainDirectory();
        mainDirectoryRepository.save(mainDirectory);

        Plan plan1 = Plan.builder()
                .member(member1)
                .name("test")
                .destination("test")
                .periods(3)
                .depart("test")
                .thumbnail("test")
                .planStatus(PlanStatus.MAIN)
                .build();

        Plan plan2 = Plan.builder()
                .member(member1)
                .name("test2")
                .destination("test2")
                .periods(4)
                .depart("test2")
                .thumbnail("test2")
                .planStatus(PlanStatus.MAIN)
                .build();

        planRepository.save(plan1);
        planRepository.save(plan2);

        em.flush();
        em.clear();

        List<Plan> all = planRepository.findAll();
        List<Location> allLocations = locationRepository.findAll();

        for (Location allLocation : allLocations) {
            SelectedLocation selectedLocation = SelectedLocation.builder()
                    .location(allLocation)
                    .member(member1)
                    .plan(plan1)
                    .build();
            selectedLocationRepository.save(selectedLocation);
        }

        em.flush();
        em.clear();

        List<SelectedLocation> allSelectedLocation = selectedLocationRepository.findAll();

        for (Plan plan : all) {
            for (int i = 1; i <= plan.getPeriods(); i++) {
                for (SelectedLocation selectedLocation : allSelectedLocation) {
                    Day day = Day.builder()
                            .plan(plan)
                            .days(i)
                            .locations(selectedLocation.getLocation())
                            //.movingData(MovingData.builder()
                            //        .movingTime("1111test")
                            //        .startTime("1111test")
                            //        .stayTime("1111test")
                            //        .vehicle("1111test").build())
                            .build();
                    dayRepository.save(day);
                }
            }
        }

        em.flush();
        em.clear();

        //when

        //then
     }
}
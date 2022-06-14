package com.trablock.web.entity.plan;

import com.trablock.web.entity.member.Member;
//import com.trablock.web.repository.MainDirectoryRepository;
import com.trablock.web.repository.member.MemberRepository;
//import com.trablock.web.repository.PlanRepository;
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
class PlanTest {

    @PersistenceContext
    EntityManager em;
//    @Autowired PlanRepository planRepository;
    @Autowired MemberRepository memberRepository;
//    @Autowired MainDirectoryRepository mainDirectoryRepository;

    @Test
    public void planEntityTest() throws Exception {
        //given
        Member member1 = Member.builder()
                .userName("member1")
                .build();
        Member member2 = Member.builder()
                .userName("member2")
                .build();
        memberRepository.save(member1);
        memberRepository.save(member2);

        UserDirectory mainDirectory = new UserDirectory();
//        mainDirectoryRepository.save(mainDirectory);


        Plan plan1 = Plan.builder()
                .member(member1)
                .name("test")
//                .destination("test")
                .periods(3)
                .depart("test")
                .thumbnail("test")
                .planStatus(PlanStatus.MAIN)
                .build();

        Plan plan2 = Plan.builder()
                .member(member1)
                .name("test")
//                .destination("test")
                .periods(3)
                .depart("test")
                .thumbnail("test")
                .planStatus(PlanStatus.MAIN)
                .build();

        Plan plan3 = Plan.builder()
                .member(member1)
                .name("test")
//                .destination("test")
                .periods(3)
                .depart("test")
                .thumbnail("test")
                .planStatus(PlanStatus.MAIN)
                .build();

//        planRepository.save(plan1);
//        planRepository.save(plan2);
//        planRepository.save(plan3);


        em.flush();
        em.clear();

        //when
        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();

        for (Member member : members) {
            System.out.println("member = " + member);
        }

        List<Plan> plans = em.createQuery("select p from Plan p", Plan.class)
                .getResultList();

        for (Plan plan : plans) {
            System.out.println("plan = " + plan);
        }


        //then
        assertThat(members.size()).isEqualTo(2);
        assertThat(plans.size()).isEqualTo(3);
     }
}
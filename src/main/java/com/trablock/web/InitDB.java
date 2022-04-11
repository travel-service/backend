package com.trablock.web;

import com.trablock.web.entity.member.Member;
import com.trablock.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final MemberRepository memberRepository;

        public void dbInit() {
            Member member1 = new Member("member1");
            memberRepository.save(member1);

            Member member2 = new Member("member2");
            memberRepository.save(member2);

            Member member3 = new Member("member3");
            memberRepository.save(member3);

            Member member4 = new Member("member4");
            memberRepository.save(member4);
        }
    }
}

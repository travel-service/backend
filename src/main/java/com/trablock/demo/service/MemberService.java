package com.trablock.demo.service;

import com.trablock.demo.domain.member.Member;
import com.trablock.demo.dto.MemberSaveRequestDto;
import com.trablock.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder; // Password 난수화 encoder

    @Transactional
    public Long save(MemberSaveRequestDto requestDto) {
        validateDuplicateMember(requestDto); /** 중복 회원 발생 시 white label, 뒷 처리 구현 X */
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        return memberRepository.save(requestDto.toEntity()).getId();
    }

    //== 중복회원 검사 메소드 ==//
    public void validateDuplicateMember(MemberSaveRequestDto requestDto) {
        Optional<Member> findMembers = memberRepository.findByUsername(requestDto.getUsername());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

}

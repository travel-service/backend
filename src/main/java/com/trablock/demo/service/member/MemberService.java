package com.trablock.demo.service.member;

import com.trablock.demo.domain.member.Member;
import com.trablock.demo.dto.member.MemberSaveRequestDto;
import com.trablock.demo.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder; // Password 난수화 encoder

    @Transactional
    public Long join(MemberSaveRequestDto requestDto) {
        validateDuplicateMember(requestDto); /** 중복 회원 발생 시 white label, 뒷 처리 구현 X */
        validateAgreementPassword(requestDto); /** 비밀번호 불일치 = white label, 뒷 처리 구현 X */
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        return memberRepository.save(requestDto.toEntity()).getId();
    }

    //== 중복회원 검사 메소드 ==//
    public void validateDuplicateMember(MemberSaveRequestDto requestDto) {
        Optional<Member> findMembers = memberRepository.findByUserId(requestDto.getUserId());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //== 비밀번호 일치 검사 메소드 ==//
    public void validateAgreementPassword(MemberSaveRequestDto requestDto) {
        if(!requestDto.getPassword().equals(requestDto.getPasswordCheck())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
        System.out.println("일치합니다");
    }



}

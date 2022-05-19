package com.trablock.web.service.member;


import com.trablock.web.config.jwt.JwtTokenProvider;
import com.trablock.web.config.jwt.JwtTokenService;
import com.trablock.web.dto.member.MemberSaveDto;
import com.trablock.web.dto.member.MemberUpdateDto;
import com.trablock.web.entity.auth.RefreshToken;
import com.trablock.web.entity.member.*;
import com.trablock.web.repository.MemberRepository;
import com.trablock.web.repository.TokenRepository;
import com.trablock.web.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.MediaType.parseMediaType;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;
    private final FileService fileService;
    private final JwtTokenService jwtTokenService;

    /**
     * 회원가입
     * @param memberSaveDto
     * @return 회원 nickName
     */
    public String MemberSignUp(MemberSaveDto memberSaveDto) {
        boolean CanSignUp = MemberValidation(memberSaveDto.getUserName()); // 아이디 중복 검사

        if (CanSignUp) {
            return memberRepository.save(Member.builder()
                            .userName(memberSaveDto.getUserName())
                            .password(passwordEncoder.encode(memberSaveDto.getPassword()))
                            .realName(memberSaveDto.getRealName())
                            .memberProfile(new MemberProfile(memberSaveDto.getNickName(), null))
                            .memberInfo(new MemberInfo(memberSaveDto.getBirthday(), Gender.valueOf(memberSaveDto.getGender()),
                                    memberSaveDto.getPhoneNum(), memberSaveDto.getEmail()))
                            .roles(Collections.singletonList("ROLE_USER")) // 일반 유저
                            .build()).getMemberProfile().getNickName();
        } else throw new IllegalArgumentException("중복 되는 아이디 존재");
    }

    /**
     * 로그인
     * @param loginForm
     * @param response
     * @return 회원 nickName
     */
    public String MemberLogin(LoginForm loginForm, HttpServletResponse response) {
        Member member = memberRepository.findByUserName(loginForm.getUserName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!passwordEncoder.matches(loginForm.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(member.getUsername(), member.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getUsername(), member.getRoles());
        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);

        tokenRepository.save(new RefreshToken(refreshToken));

        return member.getMemberProfile().getNickName();
    }

    /**
     * 회원 마이페이지 DATA return
     * @param request
     * @return nickname, bio, + .. 추가 가능
     */
    public ResponseEntity<?> MemberPage(HttpServletRequest request) {
        String userName = jwtTokenService.TokenToUserName(request);
        Member member = memberRepository.findByUserName(userName).get();

        // 회원 닉네임 + ..
        String nickName = jwtTokenService.TokenToNickName(request);
        String bio = member.getMemberProfile().getBio();

        Map<String, Object> res = new HashMap<>();
        res.put("nickName", nickName);
        res.put("bio", bio);

        return ResponseEntity.ok()
                .body(res);
    }

    /**
     * 회원 프로필 사진
     * @param request
     * @return MemberImg
     * @throws FileNotFoundException
     */
    public ResponseEntity<?> MemberImg(HttpServletRequest request) throws FileNotFoundException {
        String fileName = jwtTokenService.TokenToUserName(request) + ".png";

        Resource fileResource = fileService.loadFile(fileName);
        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(fileResource.getFile().getAbsolutePath());
        } catch (IOException e) {
            log.error("Could not determine file type.");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.getFilename() + "\"")
                .body(fileResource);
    }

    /**
     * 회원의 개인정보 수정 페이지
     * @param request
     * @return MemberProfile, MemberInfo (닉네임, 회원사진, 생일, 성별, 전화번호, 이메일)
     */
    public ResponseEntity<?> MemberEditPage(HttpServletRequest request) {
        String userName = jwtTokenService.TokenToUserName(request);
        Member member = memberRepository.findByUserName(userName).get();

        MemberProfile mp = member.getMemberProfile();
        MemberInfo mi = member.getMemberInfo();

        Map<String, Object> res = new HashMap<>();
        res.put("Profile", mp);
        res.put("Information", mi);

        return ResponseEntity.ok().body(res);
    }

    /**
     * 회원 한 줄 소개 수정 메소드
     * @param bio
     * @param request
     */
    public void UpdateComment(String bio, HttpServletRequest request) {
        Long id = jwtTokenService.TokenToUserId(request);
        Member member = memberRepository.findMemberId(id);
        member.getMemberProfile().setBio(bio);
        memberRepository.save(member);
    }

    /**
     * 회원 개인정보 수정 (아이디, 비밀번호, 프사 제외)
     * @param memberUpdateDto
     * @param request
     */
    public void updateMember(MemberUpdateDto memberUpdateDto, HttpServletRequest request) {
        Long id = jwtTokenService.TokenToUserId(request);
        Member member = memberRepository.findMemberId(id);

        member.getMemberProfile().setNickName(memberUpdateDto.getNickName());
        member.getMemberProfile().setBio(memberUpdateDto.getBio());
        member.getMemberInfo().setBirthday(memberUpdateDto.getBirthday());
        member.getMemberInfo().setEmail(memberUpdateDto.getEmail());
        member.getMemberInfo().setGender(memberUpdateDto.getGender());
        member.getMemberInfo().setPhoneNum(memberUpdateDto.getPhoneNum());

        memberRepository.save(member);
    }

    // 회원가입한 사용자인지 검증
    public boolean MemberValidation(String userName) {
        Optional<Member> member = memberRepository.findByUserName(userName);

        if (member.isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }
}

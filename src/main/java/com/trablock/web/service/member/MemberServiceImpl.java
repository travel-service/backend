package com.trablock.web.service.member;


import com.trablock.web.config.jwt.JwtTokenProvider;
import com.trablock.web.config.jwt.JwtTokenService;
import com.trablock.web.controller.exception.MemberException;
import com.trablock.web.dto.mail.EmailAuthDto;
import com.trablock.web.dto.mail.MailDto;
import com.trablock.web.dto.member.MemberPwdDto;
import com.trablock.web.dto.member.MemberSaveDto;
import com.trablock.web.dto.member.MemberUpdateDto;
import com.trablock.web.entity.auth.RefreshToken;
import com.trablock.web.entity.member.*;
import com.trablock.web.repository.member.EmailAuthRepository;
import com.trablock.web.repository.member.MemberRepository;
import com.trablock.web.repository.member.TokenRepository;
import com.trablock.web.service.file.FileService;
import com.trablock.web.service.mail.MailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.http.MediaType.parseMediaType;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private final EmailAuthRepository emailAuthRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final FileService fileService;
    private final JwtTokenService jwtTokenService;
    private final MailServiceImpl mailService;

    /**
     * 회원가입
     * @param memberSaveDto
     * @return 회원 nickName
     */
    @Override
    public String memberSignUp(MemberSaveDto memberSaveDto) {
        boolean CanSignUp = memberValidation(memberSaveDto.getUserName()); // 아이디 중복 검사
        String pwd = passwordEncoder.encode(memberSaveDto.getPassword());

        if (!CanSignUp) {
            EmailAuth emailAuth = emailAuthRepository.save(EmailAuth.builder(memberSaveDto).build());
            Member member =  memberRepository.save(Member.builder(memberSaveDto, pwd).build());

            MailDto mailDto = mailService.emailAuthMail(emailAuth.getEmail(), emailAuth.getUuid());
            mailService.sendMail(mailDto); // 메일 전송 (구글 메일 서버)

            return member.getMemberProfile().getNickName();

        } else throw new IllegalArgumentException("중복 되는 아이디 존재");
    }

    @Override
    public String confirmEmail(EmailAuthDto requestDto) {
        EmailAuth emailAuth = emailAuthRepository.findValidAuthByEmail(requestDto.getEmail(), requestDto.getUuid(), LocalDateTime.now())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 UUID"));
        Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(MemberException::new);
        emailAuth.useToken();
        member.emailVerifiedSuccess();

        return "인증이 완료되었습니다.";
    }

    /**
     * 로그인
     * @param loginForm
     * @param response
     * @return 회원 nickName
     */
    @Override
    public String memberLogin(LoginForm loginForm, HttpServletResponse response) {
        Member member = memberRepository.findByUserName(loginForm.getUserName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!passwordEncoder.matches(loginForm.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(member.getUsername(), member.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getUsername(), member.getRoles());
        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);

        tokenRepository.save(RefreshToken.builder().refreshToken(refreshToken).build());

        return member.getMemberProfile().getNickName();
    }

    /**
     * 회원 로그아웃
     * @param request
     * @return
     */
    @Override
    public String memberLogout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        Long id = tokenRepository.findByRefreshToken(refreshToken);

        tokenRepository.deleteById(id);
        jwtTokenProvider.setHeaderLogoutRefreshToken(response, "");

        return "Logout Success";
    }

    /**
     * 회원 마이페이지 DATA return
     * @param request
     * @return nickname, bio, + .. 추가 가능
     */
    @Override
    public Map<String, Object> getMemberPage(HttpServletRequest request) {
        String userName = jwtTokenService.tokenToUserName(request);
        Member member = memberRepository.findByUserName(userName).get();

        // 회원 닉네임 + ..
        String nickName = jwtTokenService.tokenToNickName(request);
        String bio = member.getMemberProfile().getBio();

        Map<String, Object> res = new HashMap<>();
        res.put("nickName", nickName);
        res.put("bio", bio);

        return res;
    }

    /**
     * 회원 프로필 사진
     * @param request
     * @return MemberImg
     * @throws FileNotFoundException
     */
    @Override
    public ResponseEntity<Resource> getMemberImg(HttpServletRequest request) throws FileNotFoundException {
//        String fileName = jwtTokenService.TokenToUserName(request) + ".png"; # 현재 이미지 처리 규칙이 없기에 잠궈놓겠습니다 (22-06-23)
        String fileName = "default_profile.png";
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
    @Override
    public Map<String, Object> memberEditPage(HttpServletRequest request) {
        String userName = jwtTokenService.tokenToUserName(request);
        Member member = memberRepository.findByUserName(userName).orElseThrow();

        MemberProfile mp = member.getMemberProfile();
        MemberInfo mi = member.getMemberInfo();

        Map<String, Object> res = new HashMap<>();
        res.put("Profile", mp);
        res.put("Information", mi);

        return res;
    }

    /**
     * 회원 한 줄 소개 수정 메소드
     * @param bio
     * @param request
     */
    @Override
    public void updateComment(String bio, HttpServletRequest request) {
        Long id = jwtTokenService.tokenToUserId(request);
        Member member = memberRepository.findMemberId(id).orElseThrow();
        member.getMemberProfile().setBio(bio);

    }

    /**
     * 회원 개인정보 수정 (아이디, 비밀번호, 프사 제외)
     * @param memberUpdateDto
     * @param request
     */
    @Override
    public void updateMember(MemberUpdateDto memberUpdateDto, HttpServletRequest request) {
        Long id = jwtTokenService.tokenToUserId(request);
        Member member = memberRepository.findMemberId(id).orElseThrow();

        member.getMemberProfile().setNickName(memberUpdateDto.getNickName());
        member.getMemberProfile().setBio(memberUpdateDto.getBio());
        member.getMemberInfo().setBirthday(memberUpdateDto.getBirthday());
        member.getMemberInfo().setEmail(memberUpdateDto.getEmail());
        member.getMemberInfo().setGender(memberUpdateDto.getGender());
    }

    /**
     * 비밀번호 찾기 (임시 비밀번호 발급)
     * @return
     */
    @Override
    public boolean getTmpPassword(Map<String, String> userInfo) {
        String userName = userInfo.get("userName");
        String userEmail = userInfo.get("userEmail");

        Optional<Member> member = Optional.of(memberRepository.findByUserName(userName).orElseThrow(() -> new MemberException("잘못된 아이디")));
        if (member.get().getMemberInfo().getEmail().equals(userEmail)) {
            String tmpPwd = passwordEncoder.encode(pwdCombination());
            memberRepository.updateMemberPwd(tmpPwd, userName);

            MailDto mail = mailService.findPwdMail(tmpPwd, userEmail);
            mailService.sendMail(mail);

            return true;
        } else throw new MemberException("아이디와 이메일이 일치하지 않습니다.");
    }

    /**
     * AccessToken을 이용하여 사용자 정보 유지하기
     * @param request
     * @return
     */
    @Override
    public Map<String, Object> getMemberInfo(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);

        if (accessToken != null) {
            if (jwtTokenProvider.validateToken(accessToken)) {
                String nickName = jwtTokenService.tokenToNickName(request);
                Map<String, Object> res = new HashMap<>();
                res.put("nickName", nickName);
                return res;
            } else throw new MemberException("Token-Error");
        }
        throw new MemberException("AccessToken 이 없습니다.");
    }

    /**
     * RefreshToken 으로 AccessToken 재발급 받기
     * @param request
     * @param response
     */
    @Override
    public boolean memberRefreshToAccess(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        if (refreshToken != null) {
            if (jwtTokenProvider.validateToken(refreshToken)) {
                String userName = jwtTokenProvider.getUserName(refreshToken);
                List<String> roles = jwtTokenProvider.getRoles(userName);

                String newAccessToken = jwtTokenProvider.createAccessToken(userName, roles);
                System.out.println("newAccessToken = " + newAccessToken);
                jwtTokenProvider.setHeaderAccessToken(response, newAccessToken);
                this.setAuthentication(newAccessToken);

                return true;
            }
        } else throw new MemberException("Token-Error");
        return false;
    }

    public void setAuthentication(String token) {
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * 회원 비밀번호 수정
      */
    @Override
    public void updateMemberPwd(HttpServletRequest request, MemberPwdDto memberPwdDto){
        String userName = jwtTokenService.tokenToUserName(request);
        Optional<Member> member = memberRepository.findByUserName(userName);

        String origin = memberPwdDto.getOriginPwd();

        if (passwordEncoder.matches(origin, member.get().getPassword())) {
            String newPwd = passwordEncoder.encode(memberPwdDto.getNewPwd());
            memberRepository.updateMemberPwd(newPwd, userName);
        } else {
            throw new MemberException("잘못된 비밀번호 입력.");
        }
    }

    /**
     * 중복 아이디 검증
     * @param userName
     * @return boolean
     */
    @Override
    public boolean memberValidation(String userName) {
        return memberRepository.existsByUserName((userName));
    }

    /**
     * 닉네임 중복 검사
     * @param nickname
     * @return boolean
     */
    @Override
    public boolean checkValidNickName(String nickname) {
        return memberRepository.existsByNickName(nickname);
    }

    /**
     * 임시비밀번호 조합
     * @return
     */
    public String pwdCombination() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String pwd = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            pwd += charSet[idx];
        }

        return pwd;
    }
}

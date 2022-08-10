package com.trablock.web.service.member;


import com.trablock.web.config.jwt.JwtTokenProvider;
import com.trablock.web.config.jwt.JwtTokenService;
import com.trablock.web.controller.exception.MemberException;
import com.trablock.web.dto.mail.EmailAuthDto;
import com.trablock.web.dto.mail.MailDto;
import com.trablock.web.dto.member.MemberPwdDto;
import com.trablock.web.dto.member.MemberResponseDto;
import com.trablock.web.dto.member.MemberSaveDto;
import com.trablock.web.dto.member.MemberUpdateDto;
import com.trablock.web.entity.auth.RefreshToken;
import com.trablock.web.entity.member.*;
import com.trablock.web.repository.member.EmailAuthRepository;
import com.trablock.web.repository.member.MemberRepository;
import com.trablock.web.repository.member.TokenRepository;
import com.trablock.web.service.file.FileService;
import com.trablock.web.service.mail.MailServiceImpl;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    private final MemberResponseDto responseDto;

    /**
     * 회원가입
     * @param memberSaveDto
     * @return 회원 nickName
     */
    @Override
    public ResponseEntity<MemberResponseDto> memberSignUp(MemberSaveDto memberSaveDto) {
        boolean CanSignUp = memberRepository.existsByUserName((memberSaveDto.getUserName())); // 아이디 중복 검사
        String pwd = passwordEncoder.encode(memberSaveDto.getPassword());

        if (!CanSignUp) {
            EmailAuth emailAuth = emailAuthRepository.save(EmailAuth.builder(memberSaveDto).build());
            memberRepository.save(Member.builder(memberSaveDto, pwd).build());

            MailDto mailDto = mailService.emailAuthMail(emailAuth.getEmail(), emailAuth.getUuid());
            mailService.sendMail(mailDto); // 메일 전송 (구글 메일 서버)

            MemberResponseDto res = responseDto.successMemberSignUp(memberSaveDto.getNickName());
            return ResponseEntity.status(HttpStatus.CREATED).body(res);

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseDto.failMemberSignUp());

    }

    @Override
    public ResponseEntity<MemberResponseDto> confirmEmail(EmailAuthDto requestDto) {
        EmailAuth emailAuth = emailAuthRepository.findValidAuthByEmail(requestDto.getEmail(), requestDto.getUuid(), LocalDateTime.now()).orElse(null);

        if (emailAuth == null) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(responseDto.failEmailAuth());
        }

        Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(MemberException::new);
        emailAuth.useToken();
        member.emailVerifiedSuccess();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDto.successEmailAuth());
    }

    /**
     * 로그인
     * @param loginForm
     * @param response
     * @return 회원 nickName
     */
    @Override
    public ResponseEntity<MemberResponseDto> memberLogin(LoginForm loginForm, HttpServletResponse response) {
        Member member = memberRepository.findByUserName(loginForm.getUserName())
                .orElse(null);

        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto.invalidUserNameOrPwd());
        }

        if (!passwordEncoder.matches(loginForm.getPassword(), member.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto.invalidUserNameOrPwd());
        }

        String accessToken = jwtTokenProvider.createAccessToken(member.getUsername(), member.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getUsername(), member.getRoles());
        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);
        tokenRepository.save(RefreshToken.builder().refreshToken(refreshToken).build());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto.successLogin(member.getMemberProfile().getNickName()));
    }

    /**
     * 회원 로그아웃
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<MemberResponseDto> memberLogout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        Long id = tokenRepository.findByRefreshToken(refreshToken);

        tokenRepository.deleteById(id);
        jwtTokenProvider.setHeaderLogoutRefreshToken(response, "");

        return ResponseEntity.status(HttpStatus.OK).body(responseDto.successLogout());
    }

    /**
     * 회원 마이페이지 DATA return
     * @param request
     * @return nickname, bio, + .. 추가 가능
     */
    @Override
    public ResponseEntity<MemberResponseDto> getMemberPage(HttpServletRequest request) {
        String userName = jwtTokenService.tokenToUserName(request);
        Member member = memberRepository.findByUserName(userName).orElseThrow(() -> new IllegalArgumentException("일치하는 회원이 없습니다."));

        // 회원 닉네임 + ..
        String nickName = jwtTokenService.tokenToNickName(request);
        String bio = member.getMemberProfile().getBio();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto.successGetMemberPage(nickName, bio));
    }

    /**
     * 회원 프로필 사진
     * @param request
     * @return MemberImg
     * @throws FileNotFoundException
     */
    @Override
    public ResponseEntity<Object> getMemberImg(HttpServletRequest request) throws FileNotFoundException {
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
    public ResponseEntity<MemberResponseDto> memberEditPage(HttpServletRequest request) {
        String userName = jwtTokenService.tokenToUserName(request);
        Member member = memberRepository.findByUserName(userName).orElseThrow();

        MemberProfile mp = member.getMemberProfile();
        MemberInfo mi = member.getMemberInfo();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto.successGetMemberEditPage(mp, mi));
    }

    /**
     * 회원 한 줄 소개 수정 메소드
     * @param bio
     * @param request
     */
    @Override
    public ResponseEntity<MemberResponseDto> updateComment(Map<String, String> bio, HttpServletRequest request) {
        Long id = jwtTokenService.tokenToUserId(request);
        Member member = memberRepository.findMemberId(id).orElseThrow();
        member.getMemberProfile().setBio(bio.get("bio"));

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto.successEditMemberProfile(member.getMemberProfile()));

    }

    /**
     * 회원 닉네임 변경
     * @param nickname
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<MemberResponseDto> updateNickName(String nickname, HttpServletRequest request) {
        Long id = jwtTokenService.tokenToUserId(request);
        Member member = memberRepository.findMemberId(id).orElseThrow();
        member.getMemberProfile().setNickName(nickname);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto.successEditMemberProfile(member.getMemberProfile()));
    }

    /**
     * 회원 개인정보 수정 (아이디, 비밀번호, 프사 제외)
     * @param memberUpdateDto
     * @param request
     */
    @Override
    public ResponseEntity<MemberResponseDto> updateMember(MemberUpdateDto memberUpdateDto, HttpServletRequest request) {
        Long id = jwtTokenService.tokenToUserId(request);
        Member member = memberRepository.findMemberId(id).orElseThrow();

        member.getMemberProfile().setNickName(memberUpdateDto.getNickName());
        member.getMemberProfile().setBio(memberUpdateDto.getBio());
        member.getMemberInfo().setBirthday(memberUpdateDto.getBirthday());
        member.getMemberInfo().setEmail(memberUpdateDto.getEmail());
        member.getMemberInfo().setGender(memberUpdateDto.getGender());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto.successEditMemberInfo(member.getMemberProfile(), member.getMemberInfo()));
    }

    /**
     * 비밀번호 찾기 (임시 비밀번호 발급)
     * @return
     */
    @Override
    public ResponseEntity<MemberResponseDto> getTmpPassword(Map<String, String> userInfo) {
        String userName = userInfo.get("userName");
        String userEmail = userInfo.get("userEmail");

        Member member = memberRepository.findByUserName(userName).orElse(null);

        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto.notFoundUserName());
        }

        if (member.getMemberInfo().getEmail().equals(userEmail)) {
            String tmpPwd = passwordEncoder.encode(pwdCombination());
            memberRepository.updateMemberPwd(tmpPwd, userName);

            MailDto mail = mailService.findPwdMail(tmpPwd, userEmail);
            mailService.sendMail(mail);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto.successIssuePwd());

        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto.failIssuePwd());
    }

    /**
     * AccessToken을 이용하여 사용자 정보 유지하기
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<MemberResponseDto> getMemberInfo(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);

        if (accessToken != null) {
            if (jwtTokenProvider.validateToken(accessToken)) {
                String nickName = jwtTokenService.tokenToNickName(request);
                return ResponseEntity.status(HttpStatus.OK).body(responseDto.successLogin(nickName));

            } return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto.expireToken());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto.notFoundAccessToken());
    }

    /**
     * RefreshToken 으로 AccessToken 재발급 받기
     * @param request
     * @param response
     */
    @Override
    public ResponseEntity<MemberResponseDto> memberRefreshToAccess(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        if (refreshToken != null) {
            if (jwtTokenProvider.validateToken(refreshToken)) {
                String userName = jwtTokenProvider.getUserName(refreshToken);
                List<String> roles = jwtTokenProvider.getRoles(userName);
                String newAccessToken = jwtTokenProvider.createAccessToken(userName, roles);
                jwtTokenProvider.setHeaderAccessToken(response, newAccessToken);
                this.setAuthentication(newAccessToken);

                return ResponseEntity.status(HttpStatus.CREATED).body(responseDto.successCreateToken());
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto.expireToken());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto.notFoundRefreshToken());
    }

    public void setAuthentication(String token) {
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * 회원 비밀번호 수정
      */
    @Override
    public ResponseEntity<MemberResponseDto>updateMemberPwd(HttpServletRequest request, MemberPwdDto memberPwdDto){
        String userName = jwtTokenService.tokenToUserName(request);
        Optional<Member> member = memberRepository.findByUserName(userName);

        String origin = memberPwdDto.getOriginPwd();

        if (passwordEncoder.matches(origin, member.get().getPassword())) {
            String newPwd = passwordEncoder.encode(memberPwdDto.getNewPwd());
            memberRepository.updateMemberPwd(newPwd, userName);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto.successEditMemberPwd());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto.failEditMemberPwd());
    }

    /**
     * 중복 아이디 검증
     * @param userName
     * @return boolean
     */
    @Override
    public ResponseEntity<MemberResponseDto> memberValidation(String userName) {
        boolean isvalid = memberRepository.existsByUserName(userName);
        if (isvalid) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDto.duplicateUserName());
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseDto.canUseUserName());
    }

    /**
     * 닉네임 중복 검사
     * @param nickname
     * @return boolean
     */
    @Override
    public ResponseEntity<MemberResponseDto> checkValidNickName(String nickname) {
        boolean isvalid = memberRepository.existsByNickName(nickname);
        if (isvalid) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDto.duplicateNickName());
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseDto.canUseNickName());
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

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
import com.trablock.web.repository.member.EmailAuthRepositoryImpl;
import com.trablock.web.repository.member.MemberRepository;
import com.trablock.web.repository.member.TokenRepository;
import com.trablock.web.service.file.FileService;
import com.trablock.web.service.mail.MailServiceImpl;
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

    /**
     * ????????????
     * @param memberSaveDto
     * @return ?????? nickName
     */
    public String MemberSignUp(MemberSaveDto memberSaveDto) {
        boolean CanSignUp = MemberValidation(memberSaveDto.getUserName()); // ????????? ?????? ??????

        if (CanSignUp) {
            EmailAuth emailAuth = emailAuthRepository.save(
                    EmailAuth.builder()
                            .email(memberSaveDto.getEmail())
                            .uuid(UUID.randomUUID().toString())
                            .expired(false)
                            .build());

            Member member =  memberRepository.save(Member.builder()
                            .userName(memberSaveDto.getUserName())
                            .password(passwordEncoder.encode(memberSaveDto.getPassword()))
                            .emailAuth(false)
                            .memberProfile(new MemberProfile(memberSaveDto.getNickName(), null))
                            .memberInfo(new MemberInfo(memberSaveDto.getBirthday(), Gender.valueOf(memberSaveDto.getGender()),
                                     memberSaveDto.getEmail()))
                            .roles(Collections.singletonList("ROLE_USER")) // ?????? ??????
                            .build());

            MailDto mailDto = mailService.emailAuthMail(emailAuth.getEmail(), emailAuth.getUuid());
            mailService.sendMail(mailDto);

            return member.getMemberProfile().getNickName();

        } else throw new IllegalArgumentException("?????? ?????? ????????? ??????");
    }

    public String confirmEmail(EmailAuthDto requestDto) {
        EmailAuth emailAuth = emailAuthRepository.findValidAuthByEmail(requestDto.getEmail(), requestDto.getUuid(), LocalDateTime.now())
                .orElseThrow(() -> new IllegalArgumentException("????????? UUID"));
        Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(MemberException::new);
        emailAuth.useToken();
        member.emailVerifiedSuccess();

        return "????????? ?????????????????????.";
    }

    /**
     * ?????????
     * @param loginForm
     * @param response
     * @return ?????? nickName
     */
    public String MemberLogin(LoginForm loginForm, HttpServletResponse response) {
        Member member = memberRepository.findByUserName(loginForm.getUserName())
                .orElseThrow(() -> new IllegalArgumentException("???????????? ?????? ??????????????????."));

        if (!passwordEncoder.matches(loginForm.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("????????? ???????????? ?????????.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(member.getUsername(), member.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getUsername(), member.getRoles());
        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);

        tokenRepository.save(RefreshToken.builder().refreshToken(refreshToken).build());

        return member.getMemberProfile().getNickName();
    }

    /**
     * ?????? ????????????
     * @param request
     * @return
     */
    public ResponseEntity<?> MemberLogout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        Long id = tokenRepository.findByRefreshToken(refreshToken);

        tokenRepository.deleteById(id);
        jwtTokenProvider.setHeaderLogoutRefreshToken(response, "");

        return ResponseEntity.ok().body("Logout Success");
    }

    /**
     * ?????? ??????????????? DATA return
     * @param request
     * @return nickname, bio, + .. ?????? ??????
     */
    public ResponseEntity<?> getMemberPage(HttpServletRequest request) {
        String userName = jwtTokenService.TokenToUserName(request);
        Member member = memberRepository.findByUserName(userName).get();

        // ?????? ????????? + ..
        String nickName = jwtTokenService.TokenToNickName(request);
        String bio = member.getMemberProfile().getBio();

        Map<String, Object> res = new HashMap<>();
        res.put("nickName", nickName);
        res.put("bio", bio);

        return ResponseEntity.ok()
                .body(res);
    }

    /**
     * ?????? ????????? ??????
     * @param request
     * @return MemberImg
     * @throws FileNotFoundException
     */
    public ResponseEntity<?> getMemberImg(HttpServletRequest request) throws FileNotFoundException {
//        String fileName = jwtTokenService.TokenToUserName(request) + ".png"; # ?????? ????????? ?????? ????????? ????????? ????????????????????? (22-06-23)
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
     * ????????? ???????????? ?????? ?????????
     * @param request
     * @return MemberProfile, MemberInfo (?????????, ????????????, ??????, ??????, ????????????, ?????????)
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
     * ?????? ??? ??? ?????? ?????? ?????????
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
     * ?????? ???????????? ?????? (?????????, ????????????, ?????? ??????)
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

        memberRepository.save(member);
    }

    /**
     * ???????????? ?????? (?????? ???????????? ??????)
     * @return
     */
    public boolean getTmpPassword(Map<String, String> userInfo) {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String Pwd = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            Pwd += charSet[idx];
        }
        String userName = userInfo.get("userName");
        String userEmail = userInfo.get("userEmail");

        String tmpPwd = passwordEncoder.encode(Pwd);
        memberRepository.updateMemberPwd(tmpPwd, userName);

        MailDto mail = mailService.findPwdMail(Pwd, userEmail);
        mailService.sendMail(mail);

        return true;
    }

    /**
     * AccessToken??? ???????????? ????????? ?????? ????????????
     * @param request
     * @return
     */
    public ResponseEntity<?> getMemberInfo(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);

        if (accessToken != null) {
            if (jwtTokenProvider.validateToken(accessToken)) {
                String nickName = jwtTokenService.TokenToNickName(request);
                Map<String, Object> res = new HashMap<>();

                res.put("nickName", nickName);

                return ResponseEntity.ok().body(res);
            } else {
                throw new MemberException("Token-Error");
            }
        }
        throw new MemberException("AccessToken ??? ????????????.");
    }

    /**
     * RefreshToken ?????? AccessToken ????????? ??????
     * @param request
     * @param response
     * @return
     */
    public ResponseEntity<?> memberRefreshToAccess(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        if (refreshToken != null) {
            if (jwtTokenProvider.validateToken(refreshToken)) {
                String userName = jwtTokenProvider.getUserName(refreshToken);
                List<String> roles = jwtTokenProvider.getRoles(userName);

                String newAccessToken = jwtTokenProvider.createAccessToken(userName, roles);
                System.out.println("newAccessToken = " + newAccessToken);
                jwtTokenProvider.setHeaderAccessToken(response, newAccessToken);
                this.setAuthentication(newAccessToken);

                return ResponseEntity.ok().body("OK");
            }
        }
        return ResponseEntity.status(401).body("TOKEN - ERROR");
    }

    public void setAuthentication(String token) {
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * ?????? ???????????? ??????
      */
    public void updateMemberPwd(HttpServletRequest request, MemberPwdDto memberPwdDto){
        String userName = jwtTokenService.TokenToUserName(request);
        Optional<Member> member = memberRepository.findByUserName(userName);

        String origin = memberPwdDto.getOriginPwd();

        if (passwordEncoder.matches(origin, member.get().getPassword())) {
            String newPwd = passwordEncoder.encode(memberPwdDto.getNewPwd());
            memberRepository.updateMemberPwd(newPwd, userName);
        } else {
            throw new MemberException("????????? ???????????? ??????.");
        }
    }

    /**
     * ?????? ????????? ??????
     * @param userName
     * @return
     */
    public boolean MemberValidation(String userName) {
        Optional<Member> member = memberRepository.findByUserName(userName);
        if (member.isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * ????????? ?????? ??????
     * @param nickname
     * @return boolean
     */
    public boolean checkValidNickName(String nickname) {
        String value = memberRepository.findByNickName(nickname);
        System.out.println("value = " + value);
        if (value == null) {
            return true;
        } else {
            return false;
        }
    }
}

package com.trablock.web.service.mail;

import com.trablock.web.dto.mail.MailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {
    private final JavaMailSender jms;

    private static final String title = "TRABLOCK 임시 비밀번호 안내 이메일 입니다.";
    private static final String message = "안녕하세요. TRABLOCK 임시 비밀번호 안내 메일입니다." + "\n"
            + "회원님의 임시 비밀번호는 아래와 같습니다. 로그인 후 반드시 비밀번호를 변경해주시길 바랍니다. \n";
    private static final String fromAddress = "Trablock2022@gmail.com";

    public MailDto createMail(String tmpPwd, String userEmail) {
        MailDto mailDto = MailDto.builder()
                .toAddress(userEmail)
                .title(title)
                .message(message + tmpPwd)
                .fromAddress(fromAddress)
                .build();

        return mailDto;
    }

    public void sendMail(MailDto mailDto) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailDto.getToAddress());
        mailMessage.setSubject(mailDto.getTitle());
        mailMessage.setText(mailDto.getMessage());
        mailMessage.setFrom(mailDto.getFromAddress());
        mailMessage.setReplyTo(mailDto.getFromAddress());

        jms.send(mailMessage);
    }
}
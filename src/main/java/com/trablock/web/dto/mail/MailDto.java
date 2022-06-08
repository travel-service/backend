package com.trablock.web.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailDto {
    private String toAddress;
    private String title;
    private String message;
    private String fromAddress;
}

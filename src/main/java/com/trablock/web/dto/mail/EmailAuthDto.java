package com.trablock.web.dto.mail;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EmailAuthDto {
    private String email;
    private String uuid;
    private Boolean expired;
    private LocalDateTime expireDate;
}

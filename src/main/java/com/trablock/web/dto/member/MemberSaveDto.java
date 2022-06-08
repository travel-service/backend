package com.trablock.web.dto.member;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberSaveDto {

    private String userName;
    private String password;
    private String realName;
    private String nickName;
    private String birthday;
    private String gender;
    private String phoneNum;
    private String email;

}

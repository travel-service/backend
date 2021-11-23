package com.trablock.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter @Embeddable
public class SNSImpl implements SNS{

    private String Instagram;
    private String Facebook;
    private String Homepage;
}

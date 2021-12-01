package com.trablock.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class SNSImpl implements SNS {

    private String Instagram;
    private String Facebook;
    private String Homepage;
}

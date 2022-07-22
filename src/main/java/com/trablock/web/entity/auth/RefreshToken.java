package com.trablock.web.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String refreshToken;
}

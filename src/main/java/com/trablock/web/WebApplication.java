package com.trablock.web;

import com.trablock.web.domain.FileStorageProperties;
import com.trablock.web.service.location.mapper.TypeLocationMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class}) // 22.05.16 file service 위해 주입 (프로필, 썸네일 etc..)
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of(UUID.randomUUID().toString());
    }

    @Bean
    public TypeLocationMapper typeLocationMapper() {
        return new TypeLocationMapper();
    }

}

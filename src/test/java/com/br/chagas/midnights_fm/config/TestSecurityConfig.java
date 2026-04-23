package com.br.chagas.midnights_fm.config;

import com.br.chagas.midnights_fm.infra.security.TokenService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestSecurityConfig {

    @Bean
    public TokenService tokenService() {
        return mock(TokenService.class);
    }
}
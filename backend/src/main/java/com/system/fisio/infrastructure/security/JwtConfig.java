package com.system.fisio.infrastructure.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class JwtConfig {

    @Bean
    public JwtService jwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.expiration-minutes}") long exp
    ) {
        return new JwtService(secret, exp);
    }
}

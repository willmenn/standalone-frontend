package com.example.standalone.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "standalone.jwt")
public class JwtConfig {
    private Long expireTimeSec;
    private String secret;
    private String issuer;
}

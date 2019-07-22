package com.example.standalone.auth;

import lombok.Value;

import java.time.LocalDateTime;

@Value
class TokenAndExpiration {
    private String token;
    private LocalDateTime expiration;
}

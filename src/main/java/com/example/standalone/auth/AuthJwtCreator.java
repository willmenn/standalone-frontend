package com.example.standalone.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.time.Clock.systemUTC;
import static java.time.LocalDateTime.now;
import static java.time.ZoneOffset.UTC;
import static java.util.Date.from;

@Service
public class AuthJwtCreator {

    private JWTVerifier verifier;
    private Algorithm algorithm;
    private JwtConfig configs;

    @Autowired
    public AuthJwtCreator(JwtConfig configs) {
        this.configs = configs;
        this.algorithm = Algorithm.HMAC256(configs.getSecret());
        this.verifier = JWT.require(algorithm)
                .withIssuer(configs.getIssuer())
                .build();
    }

    TokenAndExpiration getToken() {
        LocalDateTime expirationTime = now(systemUTC())
                .plusMinutes(configs.getExpireTimeSec());
        String token = JWT.create()
                .withExpiresAt(from(expirationTime
                        .toInstant(UTC)))
                .withIssuer("auth0")
                .sign(algorithm);
        return new TokenAndExpiration(token, expirationTime);
    }

    Boolean validateToken(String token) {
        try {
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }
}
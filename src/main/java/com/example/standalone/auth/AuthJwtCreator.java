package com.example.standalone.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
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

    public AuthJwtCreator() {
        this.algorithm = Algorithm.HMAC256("secret");
        this.verifier = verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build();
    }

    TokenAndExpiration getToken() {
        LocalDateTime expirationTime = now(systemUTC())
                .plusMinutes(5);
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
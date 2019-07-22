package com.example.standalone.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.time.Clock.systemUTC;
import static java.time.LocalDateTime.now;
import static java.time.ZoneOffset.UTC;
import static java.util.Date.from;

@Service
public class AuthJwtCreator {

    TokenAndExpiration getToken() {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        LocalDateTime expirationTime = now(systemUTC())
                .plusMinutes(5);
        String token = JWT.create()
                .withExpiresAt(from(expirationTime
                        .toInstant(UTC)))
                .withIssuer("auth0")
                .sign(algorithm);
        return new TokenAndExpiration(token, expirationTime);
    }
}

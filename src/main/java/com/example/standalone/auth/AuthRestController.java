package com.example.standalone.auth;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthRestController {

    private AuthJwtCreator jwtCreator;

    @Autowired
    public AuthRestController(AuthJwtCreator jwtCreator) {
        this.jwtCreator = jwtCreator;
    }

    @PostMapping
    public AuthResponse authUser(@RequestBody AuthRequest authRequest){
        TokenAndExpiration token = jwtCreator.getToken();

        return new AuthResponse(authRequest.username,token.getToken(),token.getExpiration());
    }

    @Value
    @AllArgsConstructor
    private static class AuthResponse {
        private String username;
        private String token;
        private LocalDateTime expiration;
    }

    @Value
    @AllArgsConstructor
    private static class AuthRequest {
        private String username;
        private String password;
        private String appToken;
    }
}

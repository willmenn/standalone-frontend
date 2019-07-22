package com.example.standalone.auth;

import com.example.standalone.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthRestController {

    private AuthJwtCreator jwtCreator;
    private UserRepository repository;

    @Autowired
    public AuthRestController(AuthJwtCreator jwtCreator, UserRepository repository) {
        this.jwtCreator = jwtCreator;
        this.repository = repository;
    }

    @PostMapping
    public AuthResponse authUser(@RequestBody AuthRequest authRequest) {
        TokenAndExpiration token = jwtCreator.getToken();

        return new AuthResponse(authRequest.username, token.getToken(), token.getExpiration());
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

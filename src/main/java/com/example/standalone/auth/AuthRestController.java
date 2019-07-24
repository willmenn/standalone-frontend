package com.example.standalone.auth;

import com.example.standalone.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

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
    public ResponseEntity authUser(@RequestBody AuthRequest authRequest) {
        TokenAndExpiration token = jwtCreator.getToken();
        boolean isUsernameTheSame = repository.findByUsername(authRequest.getUsername());
        if (!isUsernameTheSame) {
           return new ResponseEntity("", UNAUTHORIZED);
        }
        return new ResponseEntity(new AuthResponse(authRequest.username, token.getToken(), token.getExpiration()), OK);
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class AuthResponse {
        private String username;
        private String token;
        private LocalDateTime expiration;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class AuthRequest {
        private String username;
        private String password;
        private String appToken;
    }
}

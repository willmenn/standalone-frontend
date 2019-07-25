package com.example.standalone.auth;

import com.example.standalone.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
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
        String role = repository.findByUsername(authRequest.getUsername(), authRequest.getPassword());
        if (role == null && role.isEmpty()) {
            return new ResponseEntity<>(EMPTY, UNAUTHORIZED);
        }
        return new ResponseEntity<>(new AuthResponse(authRequest.username,
                token.getToken(),
                role,
                token.getExpiration()), OK);
    }

    @PostMapping("/validate")
    public ResponseEntity validateJwt(@RequestBody ValidateToken validateToken) {
        Boolean isValid = jwtCreator.validateToken(validateToken.getToken());
        if (isValid) {
            return new ResponseEntity<>(new IsValidToken(isValid), ACCEPTED);
        } else {
            return new ResponseEntity<>(new IsValidToken(isValid), NOT_ACCEPTABLE);
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class IsValidToken {
        private Boolean isValid;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ValidateToken {
        private String token;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class AuthResponse {
        private String username;
        private String token;
        private String role;
        private LocalDateTime expiration;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class AuthRequest {
        private String username;
        private String password;
        private String appToken;
    }
}

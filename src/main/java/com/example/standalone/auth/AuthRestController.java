package com.example.standalone.auth;

import com.example.standalone.cache.UserCache;
import com.example.standalone.cache.UserToken;
import com.example.standalone.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthJwtCreator jwtCreator;
    private final UserRepository repository;
    private final UserCache userCache;

    @PostMapping
    public ResponseEntity authUser(@RequestBody AuthRequest authRequest) {
        TokenAndExpiration token = jwtCreator.getToken();
        String role;
        try {

            role = repository.findByUsername(authRequest.getUsername(),
                    authRequest.getPassword(),
                    authRequest.getAppToken());

        } catch (DataAccessException exception) {
            return returnUnauthorized();
        }

        if (role == null || role.isEmpty()) {
            return returnUnauthorized();
        }

        userCache.addUser(new UserToken(authRequest.getUsername(), token.getToken(), role));

        return new ResponseEntity<>(new AuthResponse(authRequest.username,
                token.getToken(),
                role,
                authRequest.getAppToken(),
                token.getExpiration()), OK);
    }

    private ResponseEntity returnUnauthorized() {
        return new ResponseEntity<>(new ErrorResponse("Invalid username/password."), UNAUTHORIZED);
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
        private String appToken;
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

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ErrorResponse {
        private String errorMessage;
    }
}

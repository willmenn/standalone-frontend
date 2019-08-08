package com.example.standalone.user;

import com.example.standalone.cache.UserCache;
import com.example.standalone.cache.UserToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private static final String DUPLICATED_USER_ERROR_MSG = "Duplicated user.";
    private final UserRepository repository;
    private final UserCache userCache;

    @PostMapping
    public ResponseEntity createUser(@RequestBody CreateUserRequest createUserRequest) {
        try {

            boolean isInserted = repository.insert(createUserRequest.username,
                    createUserRequest.password,
                    createUserRequest.role,
                    createUserRequest.app);

            return new ResponseEntity<>(new CreateUserResponse(isInserted, null), HttpStatus.OK);
        } catch (DataAccessException exception) {
            return new ResponseEntity<>(new CreateUserResponse(false, DUPLICATED_USER_ERROR_MSG), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{token}")
    public UserToken getOneUser(@PathVariable("token") String token) {
        return userCache.getUserByToken(token);
    }

    @GetMapping("/apps/{app}")
    public List<Map<String, Object>> getAllUsers(@PathVariable("app") String appToken) {
        return repository.fetchAllByApp(appToken);
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    private static class CreateUserRequest {
        private String username;
        private String password;
        private String role;
        private String app;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class CreateUserResponse {
        private boolean isInserted;
        private String errorMessage;
    }
}

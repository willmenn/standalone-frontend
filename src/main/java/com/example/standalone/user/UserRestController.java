package com.example.standalone.user;

import com.example.standalone.cache.UserCache;
import com.example.standalone.cache.UserToken;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserRepository repository;
    private final UserCache userCache;

    @PostMapping
    public boolean createUser(@RequestBody CreateUserRequest createUserRequest) {
        return repository.insert(createUserRequest.username,
                createUserRequest.password,
                createUserRequest.role,
                createUserRequest.app);
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

}

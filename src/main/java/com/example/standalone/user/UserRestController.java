package com.example.standalone.user;

import com.example.standalone.cache.UserCache;
import com.example.standalone.cache.UserToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserRestController {

    private UserRepository repository;
    private UserCache userCache;

    @Autowired
    public UserRestController(UserRepository repository, UserCache userCache) {
        this.repository = repository;
        this.userCache = userCache;
    }

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

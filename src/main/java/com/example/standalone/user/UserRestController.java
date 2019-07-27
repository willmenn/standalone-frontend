package com.example.standalone.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserRestController {

    private UserRepository repository;
    private RedisTemplate template;

    @Autowired
    public UserRestController(UserRepository repository, RedisTemplate template) {
        this.repository = repository;
        this.template = template;
    }

    @PostMapping
    public boolean createUser(@RequestBody CreateUserRequest createUserRequest) {
        template.opsForList().leftPush(createUserRequest.getUsername(), createUserRequest.toString());
        return repository.insert(createUserRequest.username,
                createUserRequest.password,
                createUserRequest.role,
                createUserRequest.app);
    }

    @GetMapping("/{username}")
    public String getOneUser(@PathVariable("username") String username){
        return (String) template.opsForList().leftPop(username);
    }

    @GetMapping
    public List<Map<String, Object>> getAllUsers() {
        return repository.fetchAll();
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

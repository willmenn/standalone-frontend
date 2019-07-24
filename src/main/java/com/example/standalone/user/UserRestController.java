package com.example.standalone.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    public UserRestController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public boolean createUser(@RequestBody CreateUserRequest createUserRequest) {
        return repository.insert(createUserRequest.username, createUserRequest.password, createUserRequest.role);
    }

    @GetMapping
    public List<Map<String, Object>> getAllUsers() {
        return repository.fetchAll();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class CreateUserRequest {
        private String username;
        private String password;
        private String role;
    }

}

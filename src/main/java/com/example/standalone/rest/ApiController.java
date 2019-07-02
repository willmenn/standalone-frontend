package com.example.standalone.rest;

import com.example.standalone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ApiController {

    private UserRepository repo;

    @Autowired
    public ApiController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping(value = "/get")
    public List<Map<String, Object>> get() {
        return repo.fetchAll();
    }
}

package com.example.standalone.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCache {

    private final RedisTemplate template;

    public void addUser(UserToken user) {
        template.opsForValue().set(user.getToken(), user);
    }

    public UserToken getUserByToken(String token) {
        return (UserToken) template.opsForValue().get(token);
    }
}

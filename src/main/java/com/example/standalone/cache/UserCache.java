package com.example.standalone.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserCache {

    private RedisTemplate template;

    @Autowired
    public UserCache(RedisTemplate template) {
        this.template = template;
    }

    public void addUser(UserToken user) {
        template.opsForList().leftPush(user.getToken(), user);
    }

    public UserToken getUserByToken(String token) {
        return (UserToken) template.opsForList().leftPop(token);
    }
}

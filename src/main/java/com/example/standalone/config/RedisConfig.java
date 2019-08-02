package com.example.standalone.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate(RedisProperties redisProperties) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory(redisProperties));
        template.setEnableTransactionSupport(true);
        return template;
    }

    JedisConnectionFactory jedisConnectionFactory(RedisProperties redisProperties) {
        JedisConnectionFactory jedisConFactory
                = new JedisConnectionFactory();
        jedisConFactory.setHostName(redisProperties.getHost());
        jedisConFactory.setPort(redisProperties.getPort());
        return jedisConFactory;
    }

    @Data
    @Configuration
    @ConfigurationProperties(prefix = "standalone.redis")
    public static class RedisProperties {
        private String host;
        private Integer port;
    }
}

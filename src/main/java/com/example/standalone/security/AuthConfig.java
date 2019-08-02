package com.example.standalone.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "standalone.auth")
public class AuthConfig {
    @NestedConfigurationProperty
    List<PathContext> paths = new ArrayList<>();

    @Data
    public static class PathContext {
        private String path;
        private String name;
        private List<String> roles;
    }
}

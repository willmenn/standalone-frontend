package com.example.standalone.security;

import com.example.standalone.cache.UserCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import java.util.HashSet;

import static java.util.EnumSet.of;
import static javax.servlet.DispatcherType.REQUEST;

@Configuration
@Slf4j
public class FilterConfig implements ServletContextInitializer {

    private AuthConfig authConfig;
    private UserCache userCache;

    @Autowired
    public FilterConfig(AuthConfig authConfig, UserCache userCache) {
        this.authConfig = authConfig;
        this.userCache = userCache;
    }

    @Override
    public void onStartup(ServletContext servletContext) {
        authConfig
                .getPaths()
                .forEach(s -> {
                    servletContext.addFilter(s.getName(),
                            new AuthFilter(userCache, new HashSet<>(s.getRoles()), s.getName()));
                    servletContext.getFilterRegistration(s.getName())
                            .addMappingForUrlPatterns(of(REQUEST), true, s.getPath());
                });
    }
}

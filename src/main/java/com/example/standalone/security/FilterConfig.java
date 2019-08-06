package com.example.standalone.security;

import com.example.standalone.cache.UserCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import java.util.HashSet;

import static java.util.EnumSet.of;
import static javax.servlet.DispatcherType.REQUEST;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class FilterConfig implements ServletContextInitializer {

    private final AuthConfig authConfig;
    private final UserCache userCache;

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

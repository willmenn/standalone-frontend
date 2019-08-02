package com.example.standalone.security;

import com.example.standalone.cache.UserCache;
import com.example.standalone.cache.UserToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import static javax.servlet.DispatcherType.REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

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
    public void onStartup(ServletContext servletContext) throws ServletException {
        authConfig
                .getPaths()
                .forEach(s -> {
                    servletContext.addFilter(s.getName(),
                            new RequestResponseLoggingFilter(userCache, new HashSet<>(s.getRoles()), s.getName()));
                    servletContext.getFilterRegistration(s.getName())
                            .addMappingForUrlPatterns(EnumSet.of(REQUEST), true, s.getPath());
                });
    }

    @Slf4j
    private static class RequestResponseLoggingFilter implements Filter {

        private static final String BEARER_PREFIX = "Bearer";
        private static final String AUTHORIZATION_HEADER = "Authorization";
        private UserCache userCache;

        private Set<String> roles;
        private String name;

        RequestResponseLoggingFilter(UserCache userCache, Set<String> roles, String name) {
            this.userCache = userCache;
            this.roles = roles;
            this.name = name;
        }

        @Override
        public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
            log.info("########## Initiating CustomURLFilter with name {} ##########", this.name);
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {

            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;

            String jwtToken = req.getHeader(AUTHORIZATION_HEADER);

            if (jwtToken == null) {

                res.setStatus(FORBIDDEN.value());

            } else {

                String token = removeBearerPrefix(jwtToken);

                UserToken userDetails = userCache.getUserByToken(token.trim());

                if (userDetails != null && roles.contains(userDetails.getRole())) {
                    chain.doFilter(request, response);
                } else {
                    res.setStatus(FORBIDDEN.value());
                }

            }
        }

        private String removeBearerPrefix(String jwtToken) {
            return jwtToken.replace(BEARER_PREFIX, "");
        }

        @Override
        public void destroy() {

        }
    }
}

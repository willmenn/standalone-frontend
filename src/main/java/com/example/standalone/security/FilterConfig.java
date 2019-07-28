package com.example.standalone.security;

import com.example.standalone.cache.UserCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RequestResponseLoggingFilter> loggingFilter(RequestResponseLoggingFilter filter) {
        FilterRegistrationBean<RequestResponseLoggingFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/users/*");

        return registrationBean;
    }

    @Component
    @Slf4j
    private static class RequestResponseLoggingFilter implements Filter {
        private UserCache userCache;

        @Autowired
        public RequestResponseLoggingFilter(UserCache userCache) {
            this.userCache = userCache;
        }

        @Override
        public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {

        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;

            String jwtToken = req.getHeader("Authorization");
            if (jwtToken != null) {
                String token = jwtToken.replace("Bearer", "");

                if ( userCache.getUserByToken(token.trim()) != null) {
                    chain.doFilter(request, response);
                } else {
                    res.setStatus(HttpStatus.FORBIDDEN.value());
                }

            } else {
                res.setStatus(HttpStatus.FORBIDDEN.value());
            }
        }

        @Override
        public void destroy() {

        }
    }
}

package com.example.standalone.security;

import com.example.standalone.cache.UserCache;
import com.example.standalone.cache.UserToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Slf4j
@RequiredArgsConstructor
class AuthFilter implements Filter {

    private static final String BEARER_PREFIX = "Bearer";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final UserCache userCache;

    private final Set<String> roles;
    private final String name;

    @Override
    public void init(javax.servlet.FilterConfig filterConfig) {
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
}


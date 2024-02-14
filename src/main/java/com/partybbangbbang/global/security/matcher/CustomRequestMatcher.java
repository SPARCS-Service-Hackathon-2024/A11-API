package com.partybbangbbang.global.security.matcher;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class CustomRequestMatcher {

    public RequestMatcher customEndpoints() {
        return new OrRequestMatcher(
                new AntPathRequestMatcher("/api/v1/auth/**"),
                new AntPathRequestMatcher("/api/v1/invitation/**")
        );
    }
}

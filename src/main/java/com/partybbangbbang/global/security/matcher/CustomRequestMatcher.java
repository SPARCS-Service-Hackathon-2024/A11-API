package com.partybbangbbang.global.security.matcher;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class CustomRequestMatcher {

    public RequestMatcher authEndpoints() {
        return new OrRequestMatcher(
                new AntPathRequestMatcher("/api/v1/auth/**"),
                new AntPathRequestMatcher("/api/v1/util/**")
        );
    }

    public RequestMatcher userEndpoints() {
        return new OrRequestMatcher(
                new AntPathRequestMatcher("/api/v1/user", HttpMethod.GET.toString()),
                new AntPathRequestMatcher("/api/v1/user/all", HttpMethod.GET.toString()),
                new AntPathRequestMatcher("/api/v1/user/{id:\\d+}", HttpMethod.GET.toString()),
                new AntPathRequestMatcher("/api/v1/user/random", HttpMethod.GET.toString())
        );
    }

    public RequestMatcher errorEndpoints() {
        return new AntPathRequestMatcher("/error", HttpMethod.GET.toString());
    }
}


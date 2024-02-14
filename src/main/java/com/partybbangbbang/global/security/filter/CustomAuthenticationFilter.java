package com.partybbangbbang.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.partybbangbbang.global.security.handler.CustomAuthenticationFailureHandler;
import com.partybbangbbang.global.security.handler.CustomAuthenticationSuccessHandler;
import com.partybbangbbang.member.application.dto.request.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper om;

    public CustomAuthenticationFilter(
            AuthenticationManager authenticationManager,
            ObjectMapper om,
            CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
            CustomAuthenticationFailureHandler customAuthenticationFailureHandler
    ) {
        super(authenticationManager);
        super.setFilterProcessesUrl("/api/v1/auth/login");
        super.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        super.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        this.om = om;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(request);
        setDetails(request, authenticationToken);
        Authentication authenticate;
        try {
            authenticate = getAuthenticationManager().authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            request.setAttribute("exception", e);
            throw e;
        }
        return authenticate;
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        try {
            LoginRequest dto = om.readValue(request.getInputStream(), LoginRequest.class);
            log.debug("CustomAuthenticationFilter :: email : {}, password : {}", dto.email(), dto.password());
            return new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

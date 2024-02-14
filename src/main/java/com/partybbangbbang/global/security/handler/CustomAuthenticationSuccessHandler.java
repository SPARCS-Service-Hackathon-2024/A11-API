package com.partybbangbbang.global.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.partybbangbbang.global.security.jwt.JwtService;
import com.partybbangbbang.global.security.model.PrincipalDetails;
import com.partybbangbbang.member.application.dto.response.LoginResponse;
import com.partybbangbbang.member.domain.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper om;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        writeResponseBody(response, getJsonResponse(details.getMember(), request.getHeader("User-Agent")));
    }

    private void writeResponseBody(
            HttpServletResponse response,
            String jsonResponse
    ) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.write(jsonResponse);
    }

    private String getJsonResponse(Member member, String userAgent) throws IOException {
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Long now = System.currentTimeMillis();
        String accessToken = jwtService.getAccessToken(member, now).getEncodedBody();
        String refreshToken = jwtService.getRefreshToken(member, userAgent, now).getEncodedBody();

        LoginResponse response = new LoginResponse(member.getId(), accessToken, refreshToken, member.isMatched());

        return om.writerWithDefaultPrettyPrinter().writeValueAsString(response);
    }
}

package com.partybbangbbang.member.presentation;

import com.partybbangbbang.member.application.AuthService;
import com.partybbangbbang.member.application.TokenService;
import com.partybbangbbang.member.application.dto.request.IssuedTokensRequest;
import com.partybbangbbang.member.application.dto.request.JoinRequest;
import com.partybbangbbang.member.application.dto.response.IssuedTokensResponse;
import com.partybbangbbang.member.application.dto.response.JoinResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenService refreshTokenService;

    @PostMapping("/join")
    public ResponseEntity<JoinResponse> join(
            @RequestBody @Validated JoinRequest request,
            HttpServletRequest servletRequest
    ) {
        JoinResponse response = authService.join(request, servletRequest.getHeader("User-Agent"));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/token")
    public ResponseEntity<IssuedTokensResponse> reIssueTokens(
            @RequestBody IssuedTokensRequest request,
            HttpServletRequest servletRequest
    ) {
        IssuedTokensResponse response = refreshTokenService.issueTokens(request.refreshToken(), servletRequest.getHeader("User-Agent"));
        return ResponseEntity.ok(response);
    }
}

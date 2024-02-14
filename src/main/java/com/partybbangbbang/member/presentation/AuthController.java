package com.partybbangbbang.member.presentation;

import com.partybbangbbang.member.application.AuthService;
import com.partybbangbbang.member.application.TokenService;
import com.partybbangbbang.member.presentation.dto.request.WebIssuedTokensRequest;
import com.partybbangbbang.member.presentation.dto.request.WebJoinRequest;
import com.partybbangbbang.member.presentation.dto.response.IssuedTokensResponse;
import com.partybbangbbang.member.presentation.dto.response.JoinResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenService refreshTokenService;

    @PostMapping("/join")
    public ResponseEntity<JoinResponse> join(
            @RequestBody @Validated WebJoinRequest request,
            HttpServletRequest servletRequest
    ) {
        JoinResponse response = authService.join(request.convert(), servletRequest.getHeader("User-Agent"));
        return ResponseEntity.created(URI.create("/api/v1/user/" + response.id()))
                .body(response);
    }

    @PostMapping("/token")
    public ResponseEntity<IssuedTokensResponse> reIssueTokens(
            @RequestBody WebIssuedTokensRequest request,
            HttpServletRequest servletRequest
    ) {
        IssuedTokensResponse response = refreshTokenService.issueTokens(request.refreshToken(), servletRequest.getHeader("User-Agent"));
        return ResponseEntity.ok(response);
    }
}

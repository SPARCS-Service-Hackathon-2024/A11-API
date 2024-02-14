package com.partybbangbbang.member.presentation;

import com.partybbangbbang.member.application.AuthService;
import com.partybbangbbang.member.application.RefreshTokenService;
import com.partybbangbbang.member.application.dto.response.IssuedTokensResponse;
import com.partybbangbbang.member.application.dto.response.JoinResponse;
import com.partybbangbbang.member.presentation.dto.WebIssuedTokensRequest;
import com.partybbangbbang.member.presentation.dto.WebIssuedTokensResponse;
import com.partybbangbbang.member.presentation.dto.WebJoinRequest;
import com.partybbangbbang.member.presentation.dto.WebJoinResponse;
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
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/join")
    public ResponseEntity<WebJoinResponse> join(
            @RequestBody @Validated WebJoinRequest request,
            HttpServletRequest servletRequest
    ) {
        JoinResponse appResponse = authService.join(request.convert(), servletRequest.getHeader("User-Agent"));
        return ResponseEntity.created(URI.create("/api/v1/user/" + appResponse.id()))
                .body(WebJoinResponse.of(appResponse));
    }

    @PostMapping("/token")
    public ResponseEntity<WebIssuedTokensResponse> reIssueTokens(
            @RequestBody WebIssuedTokensRequest request,
            HttpServletRequest servletRequest
    ) {
        IssuedTokensResponse issuedTokensResponse =
                refreshTokenService.issueTokens(request.refreshToken(), servletRequest.getHeader("User-Agent"));
        return ResponseEntity.ok(WebIssuedTokensResponse.of(issuedTokensResponse));
    }
}

package com.partybbangbbang.member.presentation;

import java.net.URI;

import com.partybbangbbang.member.application.AuthService;
import com.partybbangbbang.member.application.RefreshTokenService;
import com.partybbangbbang.member.application.dto.response.AppIssuedTokensResponse;
import com.partybbangbbang.member.application.dto.response.AppJoinResponse;
import com.partybbangbbang.member.presentation.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
		AppJoinResponse appResponse = authService.join(request.convert(), servletRequest.getHeader("User-Agent"));
		return ResponseEntity.created(URI.create("/api/v1/members/" + appResponse.id()))
			.body(WebJoinResponse.of(appResponse));
	}

	@GetMapping("/check-email/{email}")
	public ResponseEntity<WebExistedResourceResponse> checkEmail(@Validated WebCheckEmailRequest request) {
		boolean isExist = authService.existsByEmail(request.email());
		return ResponseEntity.ok(new WebExistedResourceResponse(isExist));
	}

	@GetMapping("/check-nickname/{nickname}")
	public ResponseEntity<WebExistedResourceResponse> checkNickname(@Validated WebCheckNicknameRequest request) {
		boolean isExist = authService.existsByNickname(request.nickname());
		return ResponseEntity.ok(new WebExistedResourceResponse(isExist));
	}

	@PostMapping("/token")
	public ResponseEntity<WebIssuedTokensResponse> reIssueTokens(
		@RequestBody WebIssuedTokensRequest request,
		HttpServletRequest servletRequest
	) {
		AppIssuedTokensResponse issuedTokensResponse =
			refreshTokenService.issueTokens(request.refreshToken(), servletRequest.getHeader("User-Agent"));
		return ResponseEntity.ok(WebIssuedTokensResponse.of(issuedTokensResponse));
	}
}

package com.partybbangbbang.global.security.dto;

public record WebLoginResponse(
	Long id,
	String accessToken,
	String refreshToken
) {
}

package com.partybbangbbang.member.presentation.dto;

import com.partybbangbbang.member.application.dto.response.AppIssuedTokensResponse;

public record WebIssuedTokensResponse(
	Long id,
	String accessToken,
	String refreshToken
) {
	public static WebIssuedTokensResponse of(AppIssuedTokensResponse response) {
		return new WebIssuedTokensResponse(
			response.id(),
			response.accessToken(),
			response.refreshToken()
		);
	}
}

package com.partybbangbbang.member.presentation.dto;

import com.partybbangbbang.member.application.dto.response.IssuedTokensResponse;

public record WebIssuedTokensResponse(
	Long id,
	String accessToken,
	String refreshToken
) {
	public static WebIssuedTokensResponse of(IssuedTokensResponse response) {
		return new WebIssuedTokensResponse(
			response.id(),
			response.accessToken(),
			response.refreshToken()
		);
	}
}

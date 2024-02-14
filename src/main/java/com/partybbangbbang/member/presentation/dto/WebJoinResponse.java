package com.partybbangbbang.member.presentation.dto;


import com.partybbangbbang.member.application.dto.response.JoinResponse;

public record WebJoinResponse(
	Long id,
	String nickname,
	String accessToken,
	String refreshToken
) {
	public static WebJoinResponse of(JoinResponse response) {
		return new WebJoinResponse(
			response.id(),
			response.nickname(),
			response.accessToken(),
			response.refreshToken()
		);
	}
}

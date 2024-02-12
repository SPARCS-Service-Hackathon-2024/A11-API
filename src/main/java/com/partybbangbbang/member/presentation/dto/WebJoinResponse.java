package com.partybbangbbang.member.presentation.dto;


import com.partybbangbbang.member.application.dto.response.AppJoinResponse;

public record WebJoinResponse(
	Long id,
	String nickname,
	String accessToken,
	String refreshToken
) {
	public static WebJoinResponse of(AppJoinResponse response) {
		return new WebJoinResponse(
			response.id(),
			response.nickname(),
			response.accessToken(),
			response.refreshToken()
		);
	}
}

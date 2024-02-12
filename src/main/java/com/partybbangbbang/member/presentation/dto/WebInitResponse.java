package com.partybbangbbang.member.presentation.dto;


import com.partybbangbbang.member.application.dto.response.AppInitResponse;

public record WebInitResponse(
	Long id,
	String nickname,
	String accessToken,
	String refreshToken
) {
	public static WebInitResponse of(AppInitResponse response) {
		return new WebInitResponse(
			response.id(),
			response.nickname(),
			response.accessToken(),
			response.refreshToken()
		);
	}
}

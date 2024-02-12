package com.partybbangbbang.member.application.dto.request;

public record AppInitRequest(
	String nickname,
	Boolean acceptsMarketing
) {
}

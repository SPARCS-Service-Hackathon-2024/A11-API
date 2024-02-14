package com.partybbangbbang.member.application.dto.request;

public record JoinRequest(
	String email,
	String password,
	String nickname
) {
}

package com.partybbangbbang.member.application.dto.request;

public record AppJoinRequest(
	String email,
	String password,
	String nickname
) {
}

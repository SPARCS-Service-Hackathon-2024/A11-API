package com.partybbangbbang.member.presentation.dto.request;

public record JoinRequest(
        String email,
        String password,
        String nickname
) {
}

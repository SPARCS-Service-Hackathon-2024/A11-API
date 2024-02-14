package com.partybbangbbang.member.application.dto.request;

public record LoginRequest(
        String email,
        String password
) {
}

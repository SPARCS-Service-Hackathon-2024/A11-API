package com.partybbangbbang.member.application.dto.response;

public record LoginResponse(
        Long id,
        String accessToken,
        String refreshToken,
        Boolean isMatched
) {
}

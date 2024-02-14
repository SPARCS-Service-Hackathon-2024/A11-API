package com.partybbangbbang.member.application.dto.response;

public record IssuedTokensResponse(
        Long id,
        String accessToken,
        String refreshToken
) {
}

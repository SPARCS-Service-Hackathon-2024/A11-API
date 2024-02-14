package com.partybbangbbang.member.presentation.dto.response;

public record IssuedTokensResponse(
        Long id,
        String accessToken,
        String refreshToken
) {
}

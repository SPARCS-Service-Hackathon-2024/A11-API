package com.partybbangbbang.member.presentation.dto.response;

import com.partybbangbbang.member.domain.Member;

public record JoinResponse(
        Long id,
        String nickname,
        String accessToken,
        String refreshToken
) {
    public static JoinResponse of(
            Member entity,
            String accessToken,
            String refreshToken
    ) {
        return new JoinResponse(
                entity.getId(),
                entity.getNickname(),
                accessToken,
                refreshToken
        );
    }
}

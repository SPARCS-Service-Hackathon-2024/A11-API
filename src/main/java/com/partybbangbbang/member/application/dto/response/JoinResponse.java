package com.partybbangbbang.member.application.dto.response;

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

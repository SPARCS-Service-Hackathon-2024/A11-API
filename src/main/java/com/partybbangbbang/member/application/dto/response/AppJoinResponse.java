package com.partybbangbbang.member.application.dto.response;

import com.partybbangbbang.member.domain.Member;

public record AppJoinResponse(
        Long id,
        String nickname,
        String accessToken,
        String refreshToken
) {
    public static AppJoinResponse of(
			Member entity,
			String accessToken,
			String refreshToken
	) {
        return new AppJoinResponse(
                entity.getId(),
                entity.getMemberInfo().getNickname(),
                accessToken,
                refreshToken
        );
    }
}

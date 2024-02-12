package com.partybbangbbang.member.application.dto.response;


import com.partybbangbbang.member.domain.Member;

public record AppInitResponse(
        Long id,
        String nickname,
        String accessToken,
        String refreshToken
) {
    public static AppInitResponse of(
			Member entity,
			String accessToken,
			String refreshToken
	) {
        return new AppInitResponse(
                entity.getId(),
                entity.getMemberInfo().getNickname(),
                accessToken,
                refreshToken
        );
    }
}

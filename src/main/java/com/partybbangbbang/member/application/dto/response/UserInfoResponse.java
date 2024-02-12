package com.partybbangbbang.member.application.dto.response;

import lombok.Builder;

@Builder
public record UserInfoResponse(
        String nickname
) {
}

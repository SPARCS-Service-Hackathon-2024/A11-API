package com.partybbangbbang.member.application.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record MatchRequest(
        @NotNull(message = "초대 코드가 유효하지 않습니다.")
        String invitationCode,

        @NotNull(message = "아기 이름이 유효하지 않습니다.")
        String babyName,

        @NotEmpty(message = "주 양육자 요청이 유효하지 않습니다.")
        @NotNull(message = "주 양육자 요청이 유효하지 않습니다.")
        Boolean receiverPrimaryStatus
) {
}

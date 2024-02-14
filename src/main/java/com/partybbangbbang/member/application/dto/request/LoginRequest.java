package com.partybbangbbang.member.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @NotNull(message = "이메일이 유효하지 않습니다.")
        String email,

        @NotNull(message = "비밀번호가 유효하지 않습니다.")
        String password
) {
}

package com.partybbangbbang.member.application.dto.request;

import com.partybbangbbang.member.domain.constants.Sex;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record JoinRequest(
        @NotNull(message = "이메일을 반드시 입력해주세요.")
        @Pattern(
                regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$",
                message = "이메일 형식을 지켜주세요.")
        String email,

        @Length(min = 8, max = 16, message = "비밀번호를 8 ~ 16글자로 입력해주세요.")
        @Pattern(
                regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).*",
                message = "영문, 숫자, 특수문자가 반드시 조합되어야 합니다.")
        String password,

        @Length(min = 1, max = 4, message = "닉네임을 1 ~ 4글자로 입력해주세요.")
        String nickname,

        @NotNull(message = "성별이 유효하지 않습니다.")
        Sex sex
) {
}

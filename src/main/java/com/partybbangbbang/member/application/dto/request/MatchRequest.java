package com.partybbangbbang.member.application.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record MatchRequest(
        @NotNull(message = "초대 코드가 유효하지 않습니다.")
        String invitationCode,

        @NotNull(message = "아기 이름이 유효하지 않습니다.")
        @Length(min = 1, max = 4, message = "아기 이름을 1~4글자로 설정해주세요.")
        @Pattern(
                regexp = "^[가-힣]+$",
                message = "아이 이름은 한글만 입력 가능합니다.")
        String babyName,

        @NotEmpty(message = "주 양육자 요청이 유효하지 않습니다.")
        @NotNull(message = "주 양육자 요청이 유효하지 않습니다.")
        Boolean receiverPrimaryStatus
) {
}

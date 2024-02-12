package com.partybbangbbang.member.presentation.dto;

import com.partybbangbbang.member.application.dto.request.AppInitRequest;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record WebInitRequest(
	@Length(min = 2, max = 6, message = "닉네임을 2 ~ 6글자로 입력해주세요.")
	String nickname,
	@NotNull(message = "해당 필드는 필수값입니다.")
	@Pattern(regexp = "true|false", message = "true 혹은 false로 입력해주세요.")
	String acceptsMarketing
) {

	public AppInitRequest convert() {
		return new AppInitRequest(nickname, Boolean.valueOf(acceptsMarketing));
	}
}

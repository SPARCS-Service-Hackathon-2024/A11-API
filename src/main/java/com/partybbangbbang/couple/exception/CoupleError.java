package com.partybbangbbang.couple.exception;

import com.partybbangbbang.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum CoupleError implements ErrorCode {

    COUPLE_NOT_FOUND("해당 사용자를 찾을 수 없습니다.", NOT_FOUND, "C_001"),
    COUPLE_SAME_SEX("같은 성별은 부부로 연결할 수 없습니다.", CONFLICT, "C_002"),
    ALREADY_COUPLE("이미 커플인 유저는 부부로 연결할 수 없습니다.", BAD_REQUEST, "C_003");

    private final String message;
    private final HttpStatus status;
    private final String code;
}

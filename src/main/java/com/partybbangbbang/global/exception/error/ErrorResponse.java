package com.partybbangbbang.global.exception.error;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.partybbangbbang.global.exception.error.GlobalError.INVALID_REQUEST_PARAM;

@Getter
public class ErrorResponse {

	private final String timeStamp;

	private final String errorCode;

	private final String errorMessage;

	private final Object details;

	private ErrorResponse(String errorCode, String errorMessage, Object details) {
		this.timeStamp = LocalDateTime.now().toString();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.details = details;
	}

	public static ErrorResponse of(ErrorCode errorCode) {
		return new ErrorResponse(errorCode.getCode(), errorCode.getMessage(), null);
	}

	public static ErrorResponse of(
			ErrorCode errorCode,
			Object details
	) {
		return new ErrorResponse(errorCode.getCode(), errorCode.getMessage(), details);
	}

	public static ErrorResponse of(Optional<FieldError> fieldError) {
		return fieldError.map(error -> new ErrorResponse(error.getCode(), error.getDefaultMessage(), null))
			.orElseGet(() -> ErrorResponse.of(INVALID_REQUEST_PARAM));
	}

	public static ErrorResponse of(List<FieldError> fieldErrors) {
		Map<String, String> errors = fieldErrors.stream()
			.collect(Collectors.toMap(FieldError::getField, err -> err.getDefaultMessage() == null ? "null" : err.getDefaultMessage()));

		return ErrorResponse.of(INVALID_REQUEST_PARAM, errors);
	}
}

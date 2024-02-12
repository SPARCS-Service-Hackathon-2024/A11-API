package com.partybbangbbang.global.security.handler;

import com.partybbangbbang.global.exception.BusinessException;
import com.partybbangbbang.global.exception.error.GlobalError;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException exception
	) {
		Throwable cause = exception.getCause();
		if (cause instanceof BusinessException) {
			throw (BusinessException) cause;
		} else {
			throw new BusinessException(GlobalError.INVALID_REQUEST_PARAM);
		}
	}
}

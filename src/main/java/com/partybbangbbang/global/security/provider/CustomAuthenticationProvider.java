package com.partybbangbbang.global.security.provider;

import com.partybbangbbang.global.exception.BusinessException;
import com.partybbangbbang.global.security.model.PrincipalDetails;
import com.partybbangbbang.member.domain.Member;
import com.partybbangbbang.member.exception.AuthError;
import com.partybbangbbang.member.infra.persistence.MemberRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final PasswordEncoder encoder;
	private final MemberRepository memberRepository;

	@Override
	public Authentication authenticate(Authentication authentication) {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

		String email = (String) token.getPrincipal();
		String password = (String) token.getCredentials();

		Member member = memberRepository.findByMemberInfoEmail(email)
			.orElseThrow(() -> new BusinessException(AuthError.BAD_EMAIL));

		if (!encoder.matches(password, member.getMemberInfo().getPassword()))
			throw new BusinessException(AuthError.BAD_PASSWORD);

		PrincipalDetails principal = new PrincipalDetails(member);
		return new UsernamePasswordAuthenticationToken(principal, password, principal.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}

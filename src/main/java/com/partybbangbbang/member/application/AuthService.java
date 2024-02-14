package com.partybbangbbang.member.application;

import com.partybbangbbang.global.exception.BusinessException;
import com.partybbangbbang.member.application.dto.request.JoinRequest;
import com.partybbangbbang.member.application.dto.response.JoinResponse;
import com.partybbangbbang.member.domain.Member;
import com.partybbangbbang.member.infra.persistence.MemberRepository;
import com.partybbangbbang.global.security.jwt.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.partybbangbbang.member.exception.MemberError.DUPLICATE_EMAIL;
import static com.partybbangbbang.member.exception.MemberError.DUPLICATE_NICKNAME;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder encoder;
	private final JwtService jwtService;

	public JoinResponse join(JoinRequest request, String userAgent) {
		if (existsByEmail(request.email()))
			throw new BusinessException(DUPLICATE_EMAIL);

		if (existsByNickname(request.nickname()))
			throw new BusinessException(DUPLICATE_NICKNAME);

		Member entity = memberRepository.save(
			Member.of(
				request.email(),
				encoder.encode(request.password()),
				request.nickname()
			)
		);

		Long now = System.currentTimeMillis();
		String accessToken = jwtService.getAccessToken(entity, now).getEncodedBody();
		String refreshToken = jwtService.getRefreshToken(entity, userAgent, now).getEncodedBody();
		return JoinResponse.of(entity, accessToken, refreshToken);
	}

	public boolean existsByEmail(String email) {
		return memberRepository.existsByMemberInfoEmail(email);
	}

	public boolean existsByNickname(String nickname) {
		return memberRepository.existsByNickname(nickname);
	}
}

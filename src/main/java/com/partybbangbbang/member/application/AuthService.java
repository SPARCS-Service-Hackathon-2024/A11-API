package com.partybbangbbang.member.application;

import com.partybbangbbang.global.exception.BusinessException;
import com.partybbangbbang.member.application.dto.request.AppJoinRequest;
import com.partybbangbbang.member.application.dto.response.AppJoinResponse;
import com.partybbangbbang.member.domain.Member;
import com.partybbangbbang.member.exception.MemberError;
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

	public AppJoinResponse join(AppJoinRequest request, String userAgent) {
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
		return AppJoinResponse.of(entity, accessToken, refreshToken);
	}

	public boolean existsByEmail(String email) {
		return memberRepository.existsByMemberInfoEmail(email);
	}

	public boolean existsByNickname(String nickname) {
		return memberRepository.existsByMemberInfoNickname(nickname);
	}
}

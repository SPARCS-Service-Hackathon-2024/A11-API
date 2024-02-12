package com.partybbangbbang.global.security.jwt;

import java.time.LocalDateTime;
import java.util.Date;

import com.partybbangbbang.global.utils.LocalDateTimeUtils;
import com.partybbangbbang.member.domain.Member;
import com.partybbangbbang.member.domain.RefreshToken;
import com.partybbangbbang.member.infra.persistence.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class JwtService {

	private final JwtFactory jwtFactory;
	private final RefreshTokenRepository refreshTokenRepository;

	public Jwt getAccessToken(Member member, Long now) {
		return jwtFactory.createAuthToken(
			String.valueOf(member.getId()), member.getMemberInfo().getRole().name(), new Date(jwtFactory.getExpiryOfAccessToken(now))
		);
	}

	public Jwt getRefreshToken(Member member, String userAgent, Long now) {
		Long expiry = jwtFactory.getExpiryOfRefreshToken(now);
		Jwt refreshToken = jwtFactory.createAuthToken(null, new Date(expiry));
		String encodedBody = refreshToken.getEncodedBody();
		LocalDateTime expiryDateTime = LocalDateTimeUtils.convertBy(expiry);

		RefreshToken entity = refreshTokenRepository.findByMemberAndUserAgent(member, userAgent)
			.orElseGet(() -> new RefreshToken(member, encodedBody, userAgent, expiryDateTime));
		entity.update(encodedBody, userAgent, expiryDateTime);
		refreshTokenRepository.save(entity);
		return refreshToken;
	}

	public void validate(String encodedBody) {
		jwtFactory.convertAuthToken(encodedBody).getTokenClaims();
	}
}

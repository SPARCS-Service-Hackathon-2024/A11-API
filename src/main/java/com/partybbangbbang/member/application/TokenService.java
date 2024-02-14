package com.partybbangbbang.member.application;

import com.partybbangbbang.global.exception.BusinessException;
import com.partybbangbbang.global.security.jwt.JwtService;
import com.partybbangbbang.member.domain.RefreshToken;
import com.partybbangbbang.member.exception.AuthError;
import com.partybbangbbang.member.infra.persistence.RefreshTokenRepository;
import com.partybbangbbang.member.presentation.dto.response.IssuedTokensResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    public IssuedTokensResponse issueTokens(
            String refreshToken,
            String userAgent
    ) {
        try {
            jwtService.validate(refreshToken);
        } catch (BusinessException e) {
            throw new BusinessException(AuthError.UNUSABLE_TOKEN);
        }

        RefreshToken entity = refreshTokenRepository.findByTokenAndUserAgent(refreshToken, userAgent)
                .orElseThrow(() -> new BusinessException(AuthError.UNUSABLE_TOKEN));

        long currentTimeMillis = System.currentTimeMillis();
        String issuedAccessToken = issueAccessToken(entity, currentTimeMillis);
        String issuedRefreshToken = issueRefreshToken(entity, userAgent, currentTimeMillis);
        return new IssuedTokensResponse(entity.getMember().getId(), issuedAccessToken, issuedRefreshToken);
    }

    public String issueRefreshToken(RefreshToken entity, String userAgent, Long currentTimeMillis) {
        return jwtService.getRefreshToken(entity.getMember(), userAgent, currentTimeMillis).getEncodedBody();
    }

    public String issueAccessToken(RefreshToken entity, Long currentTimeMillis) {
        return jwtService.getAccessToken(entity.getMember(), currentTimeMillis).getEncodedBody();
    }
}

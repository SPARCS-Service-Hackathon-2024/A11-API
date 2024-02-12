package com.partybbangbbang.member.infra.persistence;

import com.partybbangbbang.member.domain.Member;
import com.partybbangbbang.member.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMemberAndUserAgent(Member member, String userAgent);

    Optional<RefreshToken> findByTokenAndUserAgent(String token, String userAgent);
}

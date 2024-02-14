package com.partybbangbbang.global.security.jwt;

import com.partybbangbbang.global.exception.BusinessException;
import com.partybbangbbang.global.security.model.CustomUser;
import com.partybbangbbang.member.exception.AuthError;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class Jwt {

    private final Key key;

    @Getter
    private final String encodedBody;

    private static final String AUTHORITIES_KEY = "role";

    protected Jwt(
            String id,
            Date expiry,
            Key key
    ) {
        this.key = key;
        this.encodedBody = createAuthToken(id, expiry);
    }

    protected Jwt(String id, String role, Date expiry, Key key) {
        this.key = key;
        this.encodedBody = createAuthToken(id, role, expiry);
    }

    private String createAuthToken(
            String id,
            Date expiry
    ) {
        return Jwts.builder()
                .setSubject(id)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    private String createAuthToken(
            String id,
            String role,
            Date expiry
    ) {
        return Jwts.builder()
                .setSubject(id)
                .claim(AUTHORITIES_KEY, role)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    public Claims getTokenClaims() {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(encodedBody)
                    .getBody();
        } catch (SignatureException e) {
            throw new BusinessException(AuthError.INVALID_JWT_SIGNATURE);
        } catch (MalformedJwtException e) {
            throw new BusinessException(AuthError.INVALID_JWT_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new BusinessException(AuthError.EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException | IllegalArgumentException e) {
            throw new BusinessException(AuthError.UNSUPPORTED_JWT_TOKEN);
        }
    }

    public Authentication getAuthentication() {
        Claims claims = getTokenClaims();
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
                        .map(SimpleGrantedAuthority::new)
                        .toList();

        log.debug("claims subject := [{}]", claims.getSubject());
        User principal = new CustomUser(Long.parseLong(claims.getSubject()), claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, this, authorities);
    }
}

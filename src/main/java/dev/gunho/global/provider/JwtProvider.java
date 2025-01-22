package dev.gunho.toooy.global.provider;

import dev.gunho.toooy.global.dto.TooyUserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.access-time}")
    private long ACCESS_TIMEOUT;    // 1H

    @Value("${jwt.refresh-time}")
    private long REFRESH_TIMEOUT;   // 7D

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            log.error("JWT parsing error: {}", e.getMessage());
            throw new RuntimeException("JWT parsing error", e);
        }
    }

    public String generateToken(Authentication authentication, long timeout) {
        TooyUserDetail userDetails = (TooyUserDetail) authentication.getPrincipal();
        Date expiryDate = Date.from(Instant.now().plus(timeout, ChronoUnit.SECONDS));

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("idx"     , userDetails.getId())
                .claim("userId" , userDetails.getUsername())
                .claim("email"  , userDetails.getEmail())
                .claim("nick"   , userDetails.getNick())
                .claim("os"   , userDetails.getOs())
                .claim("uuid"   , userDetails.getUuid())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication, ACCESS_TIMEOUT);
    }

    public String generateRefreshToken(Authentication authentication) {
        return generateToken(authentication, REFRESH_TIMEOUT);
    }

    public Long getIdxFromToken(String token) {
        return parseClaims(token).get("idx", Long.class);
    }

    public Long getUserIdFromToken(String token) {
        return parseClaims(token).get("userId", Long.class);
    }

    public Boolean validateToken(String token) {
        try {
            parseClaims(token); // This will throw an exception if the token is invalid
            return true;
        } catch (RuntimeException e) {
            log.warn("JWT validation failed: {}", e.getMessage());
            return false;
        }
    }
}

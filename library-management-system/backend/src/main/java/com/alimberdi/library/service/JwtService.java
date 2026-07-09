package com.alimberdi.library.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {

	@Value("${jwt.secret-key}")
	private String secretKey;

	@Value("${jwt.expiration-time.access-token-minutes}")
	private long expirationMinutes;

	public SecretKey getSigningKey() {
		byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateToken(String username) {
		Instant now = Instant.now();
		Instant expiration = now.plus(expirationMinutes, ChronoUnit.MINUTES);

		return Jwts.builder()
				.subject(username)
				.issuedAt(Date.from(now))
				.expiration(Date.from(expiration))
				.signWith(getSigningKey())
				.compact();
	}

	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	public Instant extractIssuedAt(String token) {
		return extractAllClaims(token).getIssuedAt().toInstant();
	}

	public boolean isTokenValid(String token, String username) {
		String extractedUsername = extractUsername(token);
		return (extractedUsername.equals(username)) && !isTokenExpired(token);
	}

	public boolean isTokenExpired(String token) {
		Instant expiration = extractAllClaims(token).getExpiration().toInstant();
		return expiration.isBefore(Instant.now());
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

}

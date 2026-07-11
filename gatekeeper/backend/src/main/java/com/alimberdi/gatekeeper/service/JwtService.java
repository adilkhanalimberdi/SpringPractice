package com.alimberdi.gatekeeper.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtService {

	@Value("${security.jwt.secret-key}")
	private String secretKey;

	@Value("${security.jwt.expiration-minutes}")
	private long expirationMinutes;

	private SecretKey getSigningKey() {
		byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateToken(String username) {
		Instant issuedAt = Instant.now();
		Instant expiration = issuedAt.plus(expirationMinutes, ChronoUnit.MINUTES);

		return Jwts.builder()
				.subject(username)
				.issuedAt(Date.from(issuedAt))
				.expiration(Date.from(expiration))
				.signWith(getSigningKey())
				.compact();
	}

	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	public boolean isTokenValid(String token, String username) {
		String extractedUsername = extractUsername(token);
		return !isTokenExpired(token) && username.equals(extractedUsername);
	}

	private boolean isTokenExpired(String token) {
		Instant now = Instant.now();
		Instant expiration = extractAllClaims(token).getExpiration().toInstant();
		return expiration.isBefore(now);
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

}

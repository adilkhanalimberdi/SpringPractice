package com.alimberdi.library.service;

import com.alimberdi.library.entity.RefreshToken;
import com.alimberdi.library.entity.User;
import com.alimberdi.library.exception.ResourceNotFoundException;
import com.alimberdi.library.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

	private final RefreshTokenRepository repository;

	@Value("${jwt.expiration-time.refresh-token-days}")
	private long refreshTokenExpDays;

	public RefreshToken getByToken(String token) {
		return repository.findByToken(token)
				.orElseThrow(() -> new ResourceNotFoundException("Refresh token not found"));
	}

	@Transactional(rollbackFor = Exception.class)
	public RefreshToken create(User user) {
		String token = UUID.randomUUID().toString();
		String tokenFamily = UUID.randomUUID().toString();
		Instant expiryDate = Instant.now().plus(refreshTokenExpDays, ChronoUnit.DAYS);

		RefreshToken refreshToken = RefreshToken.builder()
				.token(token)
				.user(user)
				.expiryDate(expiryDate)
				.tokenFamily(tokenFamily)
				.isValid(true)
				.build();

		return repository.save(refreshToken);
	}

	@Transactional(rollbackFor = Exception.class)
	public RefreshToken createWithFamily(User user, String family) {
		String token = UUID.randomUUID().toString();
		Instant expiryDate = Instant.now().plus(refreshTokenExpDays, ChronoUnit.DAYS);

		RefreshToken refreshToken = RefreshToken.builder()
				.token(token)
				.user(user)
				.expiryDate(expiryDate)
				.tokenFamily(family)
				.isValid(true)
				.build();

		return repository.save(refreshToken);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void revokeAllByFamily(String family) {
		repository.revokeByFamily(family);
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteByUser(User user) {
		repository.deleteByUser(user);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(RefreshToken token) {
		repository.delete(token);
	}

}

package com.alimberdi.library.service;

import com.alimberdi.library.dto.AuthResponse;
import com.alimberdi.library.entity.RefreshToken;
import com.alimberdi.library.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RefreshService {

	private final JwtService jwtService;
	private final RefreshTokenService refreshTokenService;
	private final RedisBlacklistService blacklistService;

	@Transactional
	public AuthResponse refresh(String requestToken) {
		RefreshToken currentToken = refreshTokenService.getByToken(requestToken);
		User user = currentToken.getUser();

		if (!currentToken.isValid()) { // Someone stole the token and already refreshed
			blacklistService.banUserWithTimestamp(user.getUsername(), Instant.now());

			refreshTokenService.revokeAllByFamily(currentToken.getTokenFamily());
			throw new RuntimeException("Security alert: Refresh token reuse detected; Whole family  revoked;");
		}

		Instant now = Instant.now();
		if (currentToken.getExpiryDate().isBefore(now)) { // Refresh token expired
			refreshTokenService.delete(currentToken);
			throw new RuntimeException("Refresh token expired, try to sign in");
		}
		currentToken.setValid(false);

		String accessToken = jwtService.generateToken(user.getUsername());
		String newRefreshToken = refreshTokenService.createWithFamily(user, currentToken.getTokenFamily()).getToken();
		return new AuthResponse(accessToken, newRefreshToken);
	}

}

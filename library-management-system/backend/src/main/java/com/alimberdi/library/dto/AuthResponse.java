package com.alimberdi.library.dto;

public record AuthResponse(
		String accessToken,
		String refreshToken
) {}

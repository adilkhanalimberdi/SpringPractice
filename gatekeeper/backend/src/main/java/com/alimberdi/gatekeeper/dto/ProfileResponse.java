package com.alimberdi.gatekeeper.dto;

import java.time.LocalDateTime;

public record ProfileResponse(
		String username,
		String email,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {}

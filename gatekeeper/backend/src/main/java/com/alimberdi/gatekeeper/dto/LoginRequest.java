package com.alimberdi.gatekeeper.dto;

public record LoginRequest(
		String username,
		String password
) {}

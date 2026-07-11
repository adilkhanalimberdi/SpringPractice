package com.alimberdi.gatekeeper.dto;

import java.util.Map;

public record ErrorResponse(
		int statusCode,
		String message,
		Map<String, String> errors
) {
	public ErrorResponse(int statusCode, String message) {
		this(statusCode, message, null);
	}
}

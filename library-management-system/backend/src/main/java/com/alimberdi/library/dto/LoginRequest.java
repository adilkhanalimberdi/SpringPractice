package com.alimberdi.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
		@NotBlank(message = "Username cannot be empty")
		@Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
		String username,

		@NotBlank(message = "Password cannot be empty")
		@Size(min = 4, message = "Password must be at least 6 characters")
		String password
) {}

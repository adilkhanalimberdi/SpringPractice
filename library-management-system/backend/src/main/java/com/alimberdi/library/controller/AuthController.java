package com.alimberdi.library.controller;

import com.alimberdi.library.dto.AuthResponse;
import com.alimberdi.library.dto.LoginRequest;
import com.alimberdi.library.dto.RefreshRequest;
import com.alimberdi.library.dto.RegisterRequest;
import com.alimberdi.library.entity.CustomUserDetails;
import com.alimberdi.library.service.AuthService;
import com.alimberdi.library.service.RefreshService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;
	private final RefreshService refreshService;

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
		return ResponseEntity
				.ok(authService.login(request));
	}

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(authService.register(request));
	}

	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshRequest request) {
		return ResponseEntity
				.ok(refreshService.refresh(request.token()));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(@AuthenticationPrincipal CustomUserDetails userDetails) {
		authService.logout(userDetails);
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}

}

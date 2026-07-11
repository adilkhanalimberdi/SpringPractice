package com.alimberdi.gatekeeper.controller;

import com.alimberdi.gatekeeper.dto.ProfileResponse;
import com.alimberdi.gatekeeper.entity.CustomUserDetails;
import com.alimberdi.gatekeeper.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;

	@GetMapping("/profile")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<ProfileResponse> getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(userService.getProfile(userDetails.getUsername()));
	}

}

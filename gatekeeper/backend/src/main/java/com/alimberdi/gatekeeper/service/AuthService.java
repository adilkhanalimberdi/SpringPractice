package com.alimberdi.gatekeeper.service;

import com.alimberdi.gatekeeper.dto.AuthResponse;
import com.alimberdi.gatekeeper.dto.LoginRequest;
import com.alimberdi.gatekeeper.dto.RegisterRequest;
import com.alimberdi.gatekeeper.entity.User;
import com.alimberdi.gatekeeper.exception.ResourceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final JwtService jwtService;
	private final UserService userService;
	private final AuthenticationManager authenticationManager;

	public AuthResponse login(LoginRequest request) {
		authenticate(request.username(), request.password());
		User user = userService.getByUsername(request.username());
		String token = authenticate(user.getUsername(), request.password());
		return new AuthResponse(token);
	}

	public AuthResponse register(RegisterRequest request) {
		if (userService.existsByUsername(request.username())) {
			throw new ResourceAlreadyExistsException("User with this username already exists");
		}

		User user = userService.create(request);
		String token = authenticate(user.getUsername(), request.password());
		return new AuthResponse(token);
	}

	private String authenticate(String username, String password) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password)
		);
		return jwtService.generateToken(username);
	}

}

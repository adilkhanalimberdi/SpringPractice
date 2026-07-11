package com.alimberdi.library.service;

import com.alimberdi.library.dto.AuthResponse;
import com.alimberdi.library.dto.LoginRequest;
import com.alimberdi.library.dto.RegisterRequest;
import com.alimberdi.library.entity.CustomUserDetails;
import com.alimberdi.library.entity.User;
import com.alimberdi.library.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final JwtService jwtService;
	private final UserService userService;
	private final RefreshTokenService refreshTokenService;
	private final AuthenticationManager authenticationManager;
	private final RedisBlacklistService blacklistService;

	@Transactional(rollbackFor = Exception.class)
	public AuthResponse login(LoginRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password())
		);

		User user = userService.getByUsername(request.username());

		String accessToken = jwtService.generateToken(request.username());
		String refreshToken = refreshTokenService.create(user).getToken();

		blacklistService.banUserWithTimestamp(user.getUsername(), Instant.now().minusSeconds(1));

		return new AuthResponse(accessToken, refreshToken);
	}

	@Transactional(rollbackFor = Exception.class)
	public AuthResponse register(RegisterRequest request) {
		if (userService.existsByUsername(request.username())) {
			throw new UserAlreadyExistsException("User with username " + request.username() + " already exists");
		}

		User user = userService.create(request);
		String accessToken = jwtService.generateToken(request.username());
		String refreshToken = refreshTokenService.create(user).getToken();
		return new AuthResponse(accessToken, refreshToken);
	}

	@Transactional(rollbackFor = Exception.class)
	public void logout(CustomUserDetails userDetails) {
		User user = userService.getByUsername(userDetails.getUsername());
		refreshTokenService.deleteByUser(user);
	}

}

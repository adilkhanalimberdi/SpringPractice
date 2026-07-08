package com.alimberdi.library.service;

import com.alimberdi.library.dto.AuthResponse;
import com.alimberdi.library.dto.LoginRequest;
import com.alimberdi.library.dto.RegisterRequest;
import com.alimberdi.library.entity.User;
import com.alimberdi.library.enums.UserRole;
import com.alimberdi.library.exception.UserAlreadyExistsException;
import com.alimberdi.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	public AuthResponse login(LoginRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password())
		);

		String token = jwtService.generateToken(request.username());
		return new AuthResponse(token);
	}

	public AuthResponse register(RegisterRequest request) {
		if (userRepository.existsByUsername(request.username())) {
			throw new UserAlreadyExistsException("User with username " + request.username() + " already exists");
		}

		User user = User.builder()
				.username(request.username())
				.password(passwordEncoder.encode(request.password()))
				.role(UserRole.USER)
				.build();

		userRepository.save(user);

		String token = jwtService.generateToken(request.username());
		return new AuthResponse(token);
	}

}

package com.alimberdi.personalblog.service;

import com.alimberdi.personalblog.dto.RegisterRequest;
import com.alimberdi.personalblog.entity.User;
import com.alimberdi.personalblog.entity.enums.UserRole;
import com.alimberdi.personalblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;

	public void register(RegisterRequest request) {
		User user = User.builder()
				.username(request.username())
				.password(passwordEncoder.encode(request.password()))
				.role(UserRole.USER)
				.build();

		repository.save(user);
	}

	public void registerAdmin(String username, String password) {
		User user = User.builder()
				.username(username)
				.password(passwordEncoder.encode(password))
				.role(UserRole.ADMIN)
				.build();

		repository.save(user);
	}

	public boolean exists(String username) {
		return repository.existsByUsername(username);
	}

}

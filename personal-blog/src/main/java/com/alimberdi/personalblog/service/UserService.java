package com.alimberdi.personalblog.service;

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

	public User register(String username, String password) {
		User user = User.builder()
				.username(username)
				.password(passwordEncoder.encode(password))
				.role(UserRole.USER)
				.build();

		return repository.save(user);
	}

}

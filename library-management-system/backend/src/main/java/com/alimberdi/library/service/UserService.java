package com.alimberdi.library.service;

import com.alimberdi.library.dto.RegisterRequest;
import com.alimberdi.library.entity.User;
import com.alimberdi.library.enums.UserRole;
import com.alimberdi.library.exception.ResourceNotFoundException;
import com.alimberdi.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;

	public User getByUsername(String username) {
		return repository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User with name " + username + " not found"));
	}

	public boolean existsByUsername(String username) {
		return repository.existsByUsername(username);
	}

	@Transactional(rollbackFor = Exception.class)
	public User create(RegisterRequest request) {
		User user = User.builder()
				.username(request.username())
				.password(passwordEncoder.encode(request.password()))
				.role(UserRole.USER)
				.build();

		return repository.save(user);
	}

}

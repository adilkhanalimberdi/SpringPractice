package com.alimberdi.gatekeeper.service;

import com.alimberdi.gatekeeper.dto.ProfileResponse;
import com.alimberdi.gatekeeper.dto.RegisterRequest;
import com.alimberdi.gatekeeper.entity.User;
import com.alimberdi.gatekeeper.enums.UserRole;
import com.alimberdi.gatekeeper.exception.ResourceNotFoundException;
import com.alimberdi.gatekeeper.mapper.UserMapper;
import com.alimberdi.gatekeeper.repository.UserRepository;
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

	public ProfileResponse getProfile(UUID id) {
		return repository.findById(id)
				.map(UserMapper::toProfileResponse)
				.orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
	}

	public ProfileResponse getProfile(String username) {
		return repository.findByUsername(username)
				.map(UserMapper::toProfileResponse)
				.orElseThrow(() -> new ResourceNotFoundException("User with username " + username + " not found"));
	}

	public User getByUsername(String username) {
		return repository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User with username " + username + " not found"));
	}

	public boolean existsByUsername(String username) {
		return repository.existsByUsername(username);
	}

	@Transactional(rollbackFor = Exception.class)
	public User create(RegisterRequest request) {
		User user = User.builder()
				.username(request.username())
				.email(request.email())
				.password(passwordEncoder.encode(request.password()))
				.role(UserRole.USER)
				.build();

		return repository.save(user);
	}

}

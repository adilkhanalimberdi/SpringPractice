package com.alimberdi.gatekeeper.service;

import com.alimberdi.gatekeeper.entity.CustomUserDetails;
import com.alimberdi.gatekeeper.entity.User;
import com.alimberdi.gatekeeper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository repository;

	@Override
	@NullMarked
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));

		return new CustomUserDetails(user);
	}

}

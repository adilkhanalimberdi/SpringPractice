package com.alimberdi.gatekeeper.entity;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

	private final String username;
	private final String password;
	private final Collection<? extends GrantedAuthority> roles;

	public CustomUserDetails(User user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.roles = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
	}

	@Override
	@NullMarked
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public @Nullable String getPassword() {
		return password;
	}

	@Override
	@NullMarked
	public String getUsername() {
		return username;
	}

}

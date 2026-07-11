package com.alimberdi.library.filter;

import com.alimberdi.library.service.CustomUserDetailsService;
import com.alimberdi.library.service.JwtService;
import com.alimberdi.library.service.RedisBlacklistService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final CustomUserDetailsService userDetailsService;
	private final RedisBlacklistService blacklistService;

	@Override
	@NullMarked
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain
	) throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		final String token;
		final String username;

		if (authHeader == null || !authHeader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}

		token = authHeader.substring(7);
		try {
			username = jwtService.extractUsername(token);
		} catch (ExpiredJwtException ex) {
			log.warn("JWT token is expired: {}", ex.getMessage());
			log.warn(String.valueOf(request.getRequestURL()));

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json");
			response.getWriter().write("{\"statusCode\": 401, \"message\": \"JWT expired\", \"errors\": null}");
			response.getWriter().flush();
			return;
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			Optional<Instant> timestamp = blacklistService.getBanTimestamp(username);
			if (timestamp.isPresent() &&
				jwtService.extractIssuedAt(token).isBefore(timestamp.get())) {
				log.warn("Security alert: Detected request attempt with old access token");

				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Token is blacklisted");
				return;
			}

			if (jwtService.isTokenValid(token, userDetails.getUsername())) {
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
						userDetails,
						null,
						userDetails.getAuthorities()
				);

				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}

		filterChain.doFilter(request, response);
	}

}

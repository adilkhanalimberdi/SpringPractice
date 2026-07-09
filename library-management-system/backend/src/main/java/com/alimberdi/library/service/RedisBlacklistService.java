package com.alimberdi.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisBlacklistService {

	private final StringRedisTemplate redisTemplate;

	private static final String BLACKLIST_KEY_PREFIX = "blacklist:user:";

	public void banUserWithTimestamp(String username, Instant timestamp) {
		String key = BLACKLIST_KEY_PREFIX + username;
		String value = timestamp.toString();

		redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(5));
	}

	public Optional<Instant> getBanTimestamp(String username) {
		String key = BLACKLIST_KEY_PREFIX + username;
		String value = redisTemplate.opsForValue().get(key);

		if (value == null) {
			return Optional.empty();
		}

		return Optional.of(Instant.parse(value));
	}

}

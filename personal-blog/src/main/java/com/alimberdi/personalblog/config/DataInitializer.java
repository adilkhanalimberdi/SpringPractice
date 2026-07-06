package com.alimberdi.personalblog.config;

import com.alimberdi.personalblog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

	private final UserService userService;

	@Value("${app.default-admin.username}")
	private String defaultUsername;

	@Value("${app.default-admin.password}")
	private String defaultPassword;

	@Bean
	public CommandLineRunner initDefaultAdmin() {
		return args -> {
			if (!userService.exists(defaultUsername)) {
				userService.registerAdmin(defaultUsername, defaultPassword);
				log.info("Default admin user [{}] created successfully.", defaultUsername);
			}
		};
	}

}

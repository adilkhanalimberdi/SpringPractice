package com.alimberdi.urlshorteningservice.util;

import org.springframework.stereotype.Component;
import java.security.SecureRandom;

@Component
public class ShortCodeGenerator {

	private final static String ALPHABET =
			"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private final static int CODE_LENGTH = 6;

	private final static SecureRandom RANDOM = new SecureRandom();

	public String generate() {
		StringBuilder sb = new StringBuilder(CODE_LENGTH);

		for (int i = 0; i < CODE_LENGTH; i++) {
			int randomIndex = RANDOM.nextInt(ALPHABET.length());
			sb.append(ALPHABET.charAt(randomIndex));
		}

		return sb.toString();
	}

}

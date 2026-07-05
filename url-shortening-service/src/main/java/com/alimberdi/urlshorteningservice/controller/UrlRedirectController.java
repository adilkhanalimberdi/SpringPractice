package com.alimberdi.urlshorteningservice.controller;

import com.alimberdi.urlshorteningservice.service.UrlShortenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UrlRedirectController {

	private final UrlShortenerService urlShortenerService;

	@GetMapping("/go/{shortCode}")
	public ResponseEntity<?> getOriginalUrl(@PathVariable String shortCode) {
		String originalUrl = urlShortenerService.resolveOriginalUrl(shortCode);
		return ResponseEntity
				.status(HttpStatus.FOUND)
				.location(URI.create(originalUrl))
				.build();
	}

}

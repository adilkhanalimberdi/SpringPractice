package com.alimberdi.urlshorteningservice.controller;

import com.alimberdi.urlshorteningservice.dto.UrlShortenRequest;
import com.alimberdi.urlshorteningservice.dto.UrlUpdateRequest;
import com.alimberdi.urlshorteningservice.service.UrlShortenerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shorten")
@Validated
public class UrlShortenerController {

	private	final UrlShortenerService urlShortenerService;

	@GetMapping("/{shortCode}")
	public ResponseEntity<?> getUrl(
			@PathVariable
			@NotBlank(message = "you should specify shortCode")
			String shortCode
	) {
		return ResponseEntity
				.ok(urlShortenerService.resolve(shortCode));
	}

	@GetMapping("/{shortCode}/stats")
	public ResponseEntity<?> getUrlStats(
			@PathVariable
			@NotBlank(message = "you should specify shortCode")
			String shortCode
	) {
		return ResponseEntity
				.ok(urlShortenerService.resolveStats(shortCode));
	}

	@PostMapping
	public ResponseEntity<?> shortenUrl(@RequestBody @Valid UrlShortenRequest request) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(urlShortenerService.shorten(request));
	}

	@PutMapping("/{shortCode}")
	public ResponseEntity<?> updateUrl(
			@PathVariable
			@NotBlank(message = "you should specify shortCode")
			String shortCode,
			@RequestBody @Valid UrlUpdateRequest request
	) {
		return ResponseEntity
				.ok(urlShortenerService.update(shortCode, request));
	}

	@DeleteMapping("/{shortCode}")
	public ResponseEntity<?> deleteUrl(
			@PathVariable
			@NotBlank(message = "you should specify shortCode")
			String shortCode
	) {
		urlShortenerService.delete(shortCode);
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}

}

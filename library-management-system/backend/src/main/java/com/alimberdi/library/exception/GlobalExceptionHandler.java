package com.alimberdi.library.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.alimberdi.library.dto.ErrorResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ErrorResponse> handleExpiredJwtException() {
		ErrorResponse response = new ErrorResponse(
				HttpStatus.UNAUTHORIZED.value(),
				"JWT expired"
		);

		return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.body(response);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getFieldErrors().forEach(error -> {
			String fieldName = error.getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		ErrorResponse response = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				"Validation error",
				errors
		);

		return ResponseEntity
				.badRequest()
				.body(response);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundException(ResourceNotFoundException ex) {
		ErrorResponse response = new ErrorResponse(
				HttpStatus.NOT_FOUND.value(),
				ex.getMessage()
		);

		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(response);
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
		ErrorResponse response = new ErrorResponse(
				HttpStatus.CONFLICT.value(),
				ex.getMessage()
		);

		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(response);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleBadCredentialsException() {
		ErrorResponse response = new ErrorResponse(
				HttpStatus.UNAUTHORIZED.value(),
				"Invalid credentials"
		);

		return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.body(response);
	}

}

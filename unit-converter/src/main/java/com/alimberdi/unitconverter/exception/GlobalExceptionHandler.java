package com.alimberdi.unitconverter.exception;

import com.alimberdi.unitconverter.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach(error -> {
			String errorName = error.getObjectName();
			errors.put(errorName, error.getDefaultMessage());
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

	@ExceptionHandler(UnitParseFailedException.class)
	public ResponseEntity<ErrorResponse> handleUnitParseFailedException(UnitParseFailedException ex) {
		ErrorResponse response = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				ex.getMessage()
		);

		return ResponseEntity
				.badRequest()
				.body(response);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleJsonParseFailedException(HttpMessageNotReadableException ex) {
		ErrorResponse response = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				"Please, enter correct value"
		);

		return ResponseEntity
				.badRequest()
				.body(response);
	}

}

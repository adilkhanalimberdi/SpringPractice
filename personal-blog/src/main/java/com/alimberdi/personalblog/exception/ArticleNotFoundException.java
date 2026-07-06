package com.alimberdi.personalblog.exception;

public class ArticleNotFoundException extends RuntimeException {
	public ArticleNotFoundException(String message) {
		super(message);
	}
}

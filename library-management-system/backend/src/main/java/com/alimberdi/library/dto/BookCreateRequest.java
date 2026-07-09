package com.alimberdi.library.dto;

public record BookCreateRequest(
		String title,
		String authorName,
		int publishedYear,
		String isbn
) {}

package com.alimberdi.library.dto;

public record BookUpdateRequest(
		String title,
		String authorName,
		int publishedYear,
		String isbn
) {}

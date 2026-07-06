package com.alimberdi.personalblog.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ArticleCreateRequest(
		String title,
		@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate createdAt,
		String content
) {}

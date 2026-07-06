package com.alimberdi.personalblog.dto;

import java.time.LocalDate;

public record ArticleSummaryResponse(
		Long id,
		String title,
		LocalDate createdAt
) {}

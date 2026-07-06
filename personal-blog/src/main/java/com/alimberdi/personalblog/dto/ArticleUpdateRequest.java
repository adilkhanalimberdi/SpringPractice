package com.alimberdi.personalblog.dto;

public record ArticleUpdateRequest(
		String title,
		String content
) {}

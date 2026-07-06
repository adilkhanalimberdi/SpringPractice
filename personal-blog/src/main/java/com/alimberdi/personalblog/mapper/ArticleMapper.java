package com.alimberdi.personalblog.mapper;

import com.alimberdi.personalblog.dto.ArticleShortResponse;
import com.alimberdi.personalblog.dto.ArticleSummaryResponse;
import com.alimberdi.personalblog.entity.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

	public static ArticleShortResponse toShortResponse(Article article) {
		return new ArticleShortResponse(
				article.getId(),
				article.getTitle()
		);
	}

	public static ArticleSummaryResponse toSummaryResponse(Article article) {
		return new ArticleSummaryResponse(
				article.getId(),
				article.getTitle(),
				article.getCreatedAt()
		);
	}

}

package com.alimberdi.personalblog.service;

import com.alimberdi.personalblog.dto.ArticleCreateRequest;
import com.alimberdi.personalblog.dto.ArticleShortResponse;
import com.alimberdi.personalblog.dto.ArticleSummaryResponse;
import com.alimberdi.personalblog.dto.ArticleUpdateRequest;
import com.alimberdi.personalblog.entity.Article;
import com.alimberdi.personalblog.exception.ArticleNotFoundException;
import com.alimberdi.personalblog.mapper.ArticleMapper;
import com.alimberdi.personalblog.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

	private final ArticleRepository repository;

	public List<ArticleShortResponse> getAllShort() {
		return repository.findAll().stream()
				.map(ArticleMapper::toShortResponse)
				.toList();
	}

	public List<ArticleSummaryResponse> getAllSummary() {
		return repository.findAll().stream()
				.map(ArticleMapper::toSummaryResponse)
				.toList();
	}

	public Article getById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ArticleNotFoundException("Article with id " + id + " not found"));
	}

	@Transactional(rollbackFor = Exception.class)
	public Article create(ArticleCreateRequest request) {
		Article article = Article.builder()
				.title(request.title())
				.content(request.content())
				.createdAt(request.createdAt())
				.updatedAt(request.createdAt())
				.build();

		return repository.save(article);
	}

	@Transactional(rollbackFor = Exception.class)
	public void update(Long id, ArticleUpdateRequest request) {
		Article article = repository.findById(id)
				.orElseThrow(() -> new ArticleNotFoundException("Article with id " + id + " not found"));
		article.setTitle(request.title());
		article.setContent(request.content());
		article.setUpdatedAt(LocalDate.now());

		repository.save(article);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ArticleNotFoundException("Article with id " + id + " not found");
		}
		repository.deleteById(id);
	}

}

package com.alimberdi.urlshorteningservice.service;

import com.alimberdi.urlshorteningservice.dto.UrlRecordResponse;
import com.alimberdi.urlshorteningservice.dto.UrlRecordStatistics;
import com.alimberdi.urlshorteningservice.dto.UrlShortenRequest;
import com.alimberdi.urlshorteningservice.dto.UrlUpdateRequest;
import com.alimberdi.urlshorteningservice.entity.UrlRecord;
import com.alimberdi.urlshorteningservice.exception.ResourceCreationException;
import com.alimberdi.urlshorteningservice.exception.ResourceNotFoundException;
import com.alimberdi.urlshorteningservice.mapper.UrlRecordMapper;
import com.alimberdi.urlshorteningservice.repository.UrlRecordRepository;
import com.alimberdi.urlshorteningservice.util.ShortCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {

	private final UrlRecordRepository repository;
	private final ShortCodeGenerator shortCodeGenerator;

	@Transactional(rollbackFor = Exception.class)
	public String resolveOriginalUrl(String shortCode) {
		UrlRecord record = repository.findByShortCodeForUpdate(shortCode)
				.orElseThrow(() -> new ResourceNotFoundException("Original url with shortCode " + shortCode + " not found"));

		record.setAccessCount(record.getAccessCount() + 1);
		return record.getUrl();
	}

	public UrlRecordResponse resolve(String shortCode) {
		return repository.findByShortCode(shortCode)
				.map(UrlRecordMapper::toResponse)
				.orElseThrow(() -> new ResourceNotFoundException("Url record with shortCode " + shortCode + " not found"));
	}

	public UrlRecordStatistics resolveStats(String shortCode) {
		return repository.findByShortCode(shortCode)
				.map(UrlRecordMapper::toStats)
				.orElseThrow(() -> new ResourceNotFoundException("Url stats with shortCode " + shortCode + " not found"));
	}

	public UrlRecordResponse shorten(UrlShortenRequest request) {
		int maxAttempts = 5;

		for (int attempt = 0; attempt < maxAttempts; attempt++) {
			UrlRecord urlRecord = UrlRecord.builder()
					.url(request.url())
					.shortCode(shortCodeGenerator.generate())
					.accessCount(0L)
					.build();
			// CreatedAt and UpdatedAt fields will be created automatically by JPA audit

			try {
				return UrlRecordMapper.toResponse(repository.save(urlRecord));
			} catch (DataIntegrityViolationException ignored) {
			}
		}

		throw new ResourceCreationException("Failed to generate a unique short code after " + maxAttempts + " attempts");
	}

	@Transactional(rollbackFor = Exception.class)
	public UrlRecordResponse update(String shortCode, UrlUpdateRequest request) {
		UrlRecord record = repository.findByShortCodeForUpdate(shortCode)
				.orElseThrow(() ->  new ResourceNotFoundException("Url record with shortCode " + shortCode + " not found"));

		record.setUrl(request.url());
		// UpdatedAt field will be automatically updated with JPA audit

		// Dirty Checking mechanism will update it in db
		// We don't need to save manually
		return UrlRecordMapper.toResponse(record);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(String shortCode) {
		if (!repository.existsByShortCode(shortCode)) {
			throw new ResourceNotFoundException("Url record with shortCode " + shortCode + " not found");
		}

		repository.deleteByShortCode(shortCode);
	}

}

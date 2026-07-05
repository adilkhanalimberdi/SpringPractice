package com.alimberdi.urlshorteningservice.mapper;

import com.alimberdi.urlshorteningservice.dto.UrlRecordResponse;
import com.alimberdi.urlshorteningservice.dto.UrlRecordStatistics;
import com.alimberdi.urlshorteningservice.entity.UrlRecord;
import org.springframework.stereotype.Component;

@Component
public class UrlRecordMapper {

	public static UrlRecordResponse toResponse(UrlRecord urlRecord) {
		return new UrlRecordResponse(
				urlRecord.getId(),
				urlRecord.getUrl(),
				urlRecord.getShortCode(),
				urlRecord.getCreatedAt(),
				urlRecord.getUpdatedAt()
		);
	}

	public static UrlRecordStatistics toStats(UrlRecord urlRecord) {
		return new UrlRecordStatistics(
				urlRecord.getId(),
				urlRecord.getUrl(),
				urlRecord.getShortCode(),
				urlRecord.getCreatedAt(),
				urlRecord.getUpdatedAt(),
				urlRecord.getAccessCount()
		);
	}

}

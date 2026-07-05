package com.alimberdi.urlshorteningservice.repository;

import com.alimberdi.urlshorteningservice.entity.UrlRecord;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRecordRepository extends JpaRepository<UrlRecord, Long> {

	Optional<UrlRecord> findByShortCode(String shortCode);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT u FROM UrlRecord u WHERE u.shortCode = :shortCode")
	Optional<UrlRecord> findByShortCodeForUpdate(@Param("shortCode") String shortCode);

	@Query("SELECT exists(SELECT u FROM UrlRecord u WHERE u.shortCode = :shortCode)")
	Boolean existsByShortCode(@Param("shortCode") String shortCode);

	@Modifying(clearAutomatically = true)
	@Query("DELETE FROM UrlRecord u WHERE u.shortCode = :shortCode")
	void deleteByShortCode(@Param("shortCode") String shortCode);

}

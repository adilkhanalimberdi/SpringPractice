package com.alimberdi.library.repository;

import com.alimberdi.library.entity.RefreshToken;
import com.alimberdi.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

	@Query("SELECT r FROM RefreshToken r WHERE r.token = :token")
	Optional<RefreshToken> findByToken(@Param("token") String token);

	@Modifying
	@Transactional
	@Query("UPDATE RefreshToken r SET r.isValid = false WHERE r.tokenFamily = :family")
	void revokeByFamily(@Param("family") String family);

	void deleteByUser(User user);

}

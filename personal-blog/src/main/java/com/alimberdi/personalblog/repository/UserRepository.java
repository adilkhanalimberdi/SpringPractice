package com.alimberdi.personalblog.repository;

import com.alimberdi.personalblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	@Query("SELECT exists(SELECT 1 FROM User u WHERE u.username = :username)")
	boolean existsByUsername(@Param("username") String username);

}

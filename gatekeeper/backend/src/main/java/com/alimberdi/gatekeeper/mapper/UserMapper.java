package com.alimberdi.gatekeeper.mapper;

import com.alimberdi.gatekeeper.dto.ProfileResponse;
import com.alimberdi.gatekeeper.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

	public static ProfileResponse toProfileResponse(User user) {
		return new ProfileResponse(
				user.getUsername(),
				user.getEmail(),
				user.getCreatedAt(),
				user.getUpdatedAt()
		);
	}

}

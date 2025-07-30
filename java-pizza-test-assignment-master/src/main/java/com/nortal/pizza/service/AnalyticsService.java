package com.nortal.pizza.service;

import lombok.RequiredArgsConstructor;

import com.nortal.pizza.domain.UserEntity;
import com.nortal.pizza.dto.UserAnalyticsDto;
import com.nortal.pizza.repository.UserRepository;
import com.nortal.pizza.security.SpringSecuritySecurityContextProvider;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

	private final UserRepository userRepository;
	private final SpringSecuritySecurityContextProvider securityContextProvider;

	public UserAnalyticsDto calculateUserAnalytics() {
		final UserEntity userEntity = getUserEntity();
		final int id = userEntity.getId();

		return UserAnalyticsDto.builder()
				.userId(id)
				.build();
	}

	private UserEntity getUserEntity() {
		final String username = securityContextProvider.getUser().getUsername();
		return userRepository.findByUsername(username);
	}
}

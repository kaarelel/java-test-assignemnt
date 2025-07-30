package com.nortal.pizza.service;

import com.nortal.pizza.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import com.nortal.pizza.domain.UserEntity;
import com.nortal.pizza.dto.UserAnalyticsDto;
import com.nortal.pizza.repository.UserRepository;
import com.nortal.pizza.security.SpringSecuritySecurityContextProvider;

import org.hibernate.criterion.Order;
import org.springframework.security.config.web.servlet.CorsDsl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

	private final UserRepository userRepository;
	private final OrderRepository orderRepository;
	private final SpringSecuritySecurityContextProvider securityContextProvider;

	public UserAnalyticsDto calculateUserAnalytics() {
		UserEntity user = userRepository.findByUsername(securityContextProvider.getUser().getUsername());
		int userId = user.getId();

		BigDecimal average = orderRepository
				.findAveragePriceByClientId(userId)
				.setScale(2, RoundingMode.HALF_UP);

		return UserAnalyticsDto.builder()
				.userId(userId)
				.averageOrderPrice(average)
				.build();
	}

	private UserEntity getUserEntity() {
		final String username = securityContextProvider.getUser().getUsername();
		return userRepository.findByUsername(username);
	}
}

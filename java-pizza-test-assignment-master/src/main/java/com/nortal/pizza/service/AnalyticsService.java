package com.nortal.pizza.service;

import com.nortal.pizza.domain.OrderEntity;
import com.nortal.pizza.domain.PizzaEntity;
import com.nortal.pizza.dto.PizzaOrderSummaryDto;

import lombok.RequiredArgsConstructor;

import com.nortal.pizza.domain.UserEntity;
import com.nortal.pizza.dto.UserAnalyticsDto;
import com.nortal.pizza.repository.*;
import com.nortal.pizza.security.SpringSecuritySecurityContextProvider;

import org.hibernate.criterion.Order;
import org.springframework.security.config.web.servlet.CorsDsl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

	public List<PizzaOrderSummaryDto.PizzaOrders> calculatePizzaAnalytics() {
		List<OrderEntity> allOrders = (List<OrderEntity>) orderRepository.findAll(); // Fetch all orders
		long totalOrders = allOrders.size();

		Map<String, Long> countMap = allOrders.stream() // Each pizza's order count
				.flatMap(o -> o.getPizzas().stream())
				.collect(Collectors.groupingBy(
						PizzaEntity::getName,
						Collectors.counting()));

		Map<String, BigDecimal> revenueMap = allOrders.stream() // Total revenue per pizza
				.flatMap(o -> o.getPizzas().stream())
				.collect(Collectors.groupingBy(PizzaEntity::getName, Collectors.reducing(BigDecimal.ZERO, PizzaEntity::getPrice, BigDecimal::add)));

		List<PizzaOrderSummaryDto.PizzaOrders> list = countMap.entrySet().stream() // Turn both maps into a list
				.map(entry -> PizzaOrderSummaryDto.PizzaOrders.builder()
						.name(entry.getKey())
						.quantity(entry.getValue())
						.totalRevenue(revenueMap.getOrDefault(entry.getKey(), BigDecimal.ZERO)
								.setScale(2, RoundingMode.HALF_UP))
						.orderedRatio(BigDecimal.valueOf(entry.getValue())
								.divide(BigDecimal.valueOf(totalOrders), 2, RoundingMode.HALF_UP))
						.build())
				.sorted(Comparator.comparing(PizzaOrderSummaryDto.PizzaOrders::getName)) // Count, revenue (both rounded), sorted
				.collect(Collectors.toList());

		return list;
	}

	private UserEntity getUserEntity() {
		final String username = securityContextProvider.getUser().getUsername();
		return userRepository.findByUsername(username);
	}
}

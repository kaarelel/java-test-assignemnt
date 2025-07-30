package com.nortal.pizza.service;

import com.nortal.pizza.domain.OrderEntity;
import com.nortal.pizza.domain.PizzaEntity;
import com.nortal.pizza.dto.PizzaOrderSummaryDto;
import com.nortal.pizza.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportingService {

	private final OrderRepository orderRepository;
	public PizzaOrderSummaryDto getSummary() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startOfCurrentWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

		List<OrderEntity> thisWeekOrders = orderRepository
				.findOrdersFromThisWeek(startOfCurrentWeek, now);

		Map<String, Long> pizzaOrderCounts = thisWeekOrders.stream()
				.flatMap(order -> order.getPizzas().stream())
				.collect(Collectors.groupingBy(PizzaEntity::getName, Collectors.counting()));

		List<PizzaOrderSummaryDto.PizzaOrders> pizzaOrdersList = pizzaOrderCounts.entrySet().stream()
				.map(entry -> PizzaOrderSummaryDto.PizzaOrders.builder()
						.name(entry.getKey())
						.quantity(entry.getValue())
						.build()
				)
				.sorted(Comparator.comparing(PizzaOrderSummaryDto.PizzaOrders::getName))
				.collect(Collectors.toList());

		return PizzaOrderSummaryDto.builder()
				.pizzaOrders(pizzaOrdersList)
				.build();
	}
}

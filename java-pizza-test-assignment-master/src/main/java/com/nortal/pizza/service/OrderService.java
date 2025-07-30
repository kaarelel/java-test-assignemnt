package com.nortal.pizza.service;

import com.nortal.pizza.domain.OrderEntity;
import com.nortal.pizza.domain.PizzaEntity;
import com.nortal.pizza.dto.OrderDto;
import com.nortal.pizza.exception.EntityNotFoundException;
import com.nortal.pizza.repository.*;
import com.nortal.pizza.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final SpringSecuritySecurityContextProvider securityContextProvider;

	public OrderDto saveOrder(final OrderDto orderDto) {
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setAddress(orderDto.getAddress());
		orderEntity.setClient(userRepository.findByUsername(securityContextProvider.getUser().getUsername()));
		orderEntity.setPrice(new BigDecimal("9.99"));
		orderEntity = orderRepository.save(orderEntity);

		return mapEntity(orderEntity);
	}

	public OrderDto getById(final Integer id) {
		return orderRepository.findById(id)
				.map(this::mapEntity)
				.orElseThrow(() -> new EntityNotFoundException(id));
	}

	public List<OrderDto> getAllClientOrders() {
		return orderRepository.findOrdersByClientId(securityContextProvider.getUser().getUsername())
				.stream()
				.map(this::mapEntity)
				.collect(Collectors.toList());
	}

	public List<OrderDto> findByOrdersAddress(String address) {
		String lowerAddress = address.toLowerCase();
		return orderRepository.findByOrdersAddress(lowerAddress)
				.stream()
				.map(this::mapEntity)
				.collect(Collectors.toList());
	}

	private OrderDto mapEntity(final OrderEntity orderEntity) {
		return OrderDto.builder()
				.id(orderEntity.getId())
				.address(orderEntity.getAddress())
				.pizzas(orderEntity.getPizzas().stream()
						.map(PizzaEntity::getName)
						.collect(Collectors.toList()))
				.price(orderEntity.getPrice())
				.build();
	}
}

package com.nortal.pizza.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PizzaOrderSummaryDto {

	private List<PizzaOrders> pizzaOrders;

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PizzaOrders {
		private String name;
		private Long quantity;
		private BigDecimal totalRevenue;
		private BigDecimal orderedRatio;
	}
}


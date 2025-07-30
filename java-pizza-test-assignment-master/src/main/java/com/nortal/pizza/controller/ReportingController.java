package com.nortal.pizza.controller;

import com.nortal.pizza.dto.PizzaOrderSummaryDto;
import com.nortal.pizza.service.ReportingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/reports")
@RequiredArgsConstructor
public class ReportingController {

	private final ReportingService reportingService;

	@GetMapping("/weekly-pizza-orders")
	public PizzaOrderSummaryDto getWeeklyPizzaOrders() {
		return reportingService.getSummary();
	}

}

package com.nortal.pizza.service;

import com.nortal.pizza.dto.PizzaOrderSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportingService {

	public PizzaOrderSummaryDto getSummary() {
		// TASK_09 Assigment
		return new PizzaOrderSummaryDto();
	}

}

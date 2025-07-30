package com.nortal.pizza.controller;

import lombok.RequiredArgsConstructor;

import com.nortal.pizza.dto.UserAnalyticsDto;
import com.nortal.pizza.service.AnalyticsService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

	private final AnalyticsService analyticsService;

	@GetMapping("/user")
	public UserAnalyticsDto getUserAnalytics() {
		return analyticsService.calculateUserAnalytics();
	}
}

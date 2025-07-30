package com.nortal.pizza.controller;

import com.nortal.pizza.dto.OrderDto;
import com.nortal.pizza.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@PreAuthorize("principal.enabled")
@RestController
@RequestMapping(path = "/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable final Integer id) {
        return orderService.getById(id);
    }

    @GetMapping("/list")
    public List<OrderDto> getOrder() {
        return orderService.getAllClientOrders();
    }

    @PostMapping("/find-by-address")
    public List<OrderDto> findByOrdersAddress(@RequestBody final String address) {
        return orderService.findByOrdersAddress(address);
    }

    @PostMapping
    public ResponseEntity<OrderDto> addOrder(@RequestBody final OrderDto order) {
        final OrderDto createdOrder = orderService.saveOrder(order);
        return ResponseEntity.ok(createdOrder);
    }
}

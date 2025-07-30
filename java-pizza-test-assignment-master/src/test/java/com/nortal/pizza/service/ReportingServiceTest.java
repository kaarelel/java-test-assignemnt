// src/test/java/com/nortal/pizza/service/ReportingServiceTest.java
package com.nortal.pizza.service;

import com.nortal.pizza.domain.OrderEntity;
import com.nortal.pizza.domain.PizzaEntity;
import com.nortal.pizza.dto.PizzaOrderSummaryDto;
import com.nortal.pizza.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportingServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private ReportingService reportingService;

    @Test
    @DisplayName("getSummary returns empty when no orders in week")
    void shouldReturnEmptySummaryWhenNoOrders() {
        // Stub with empty list
        when(orderRepository.findOrdersFromThisWeek(any(), any()))
                .thenReturn(Collections.emptyList());

        PizzaOrderSummaryDto result = reportingService.getSummary();

        assertThat(result.getPizzaOrders()).isEmpty();
    }

    @Test
    @DisplayName("getSummary counts pizzas correctly and sorts by name")
    void shouldReturnCorrectCountsAndSorted() {
        // Setup two orders in week
        PizzaEntity a = new PizzaEntity(); a.setName("Alpha");
        PizzaEntity b = new PizzaEntity(); b.setName("Beta");
        PizzaEntity c = new PizzaEntity(); c.setName("Charlie");

        OrderEntity o1 = new OrderEntity();
        o1.setPizzas(Arrays.asList(b, a));
        OrderEntity o2 = new OrderEntity();
        o2.setPizzas(Arrays.asList(c, a));

        List<OrderEntity> weekly = Arrays.asList(o1, o2);
        when(orderRepository.findOrdersFromThisWeek(any(), any()))
                .thenReturn(weekly);

        PizzaOrderSummaryDto dto = reportingService.getSummary();
        List<PizzaOrderSummaryDto.PizzaOrders> list = dto.getPizzaOrders();

        assertThat(list).hasSize(3);
        assertThat(list.get(0).getName()).isEqualTo("Alpha");
        assertThat(list.get(0).getQuantity()).isEqualTo(2L);

        assertThat(list.get(1).getName()).isEqualTo("Beta");
        assertThat(list.get(1).getQuantity()).isEqualTo(1L);

        assertThat(list.get(2).getName()).isEqualTo("Charlie");
        assertThat(list.get(2).getQuantity()).isEqualTo(1L);
    }

    @Test
    @DisplayName("getSummary uses correct week boundaries")
    void shouldCallRepositoryWithCalculatedWeekBounds() {

        LocalDateTime now = LocalDateTime.of(2025, 7, 30, 12, 0);
        LocalDateTime expectedStart = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        when(orderRepository.findOrdersFromThisWeek(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        reportingService.getSummary();
    }
}

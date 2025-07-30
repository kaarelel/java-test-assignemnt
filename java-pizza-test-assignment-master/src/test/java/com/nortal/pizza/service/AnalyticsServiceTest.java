package com.nortal.pizza.service;

import com.nortal.pizza.domain.*;
import com.nortal.pizza.dto.UserAnalyticsDto;
import com.nortal.pizza.dto.PizzaOrderSummaryDto;
import com.nortal.pizza.repository.*;
import com.nortal.pizza.security.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("AnalyticsService Unit Tests")
class AnalyticsServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private SpringSecuritySecurityContextProvider securityContextProvider;

    @InjectMocks
    private AnalyticsService analyticsService;

    @Test
    @DisplayName("calculateUserAnalytics returns correct average")
    void shouldCalculateUserAnalytics() {
        // stub security context
        when(securityContextProvider.getUser())
                .thenReturn(new org.springframework.security.core.userdetails.User("u1", "pw", Collections.emptyList()));
        UserEntity user = new UserEntity();
        user.setId(5);
        when(userRepository.findByUsername("u1")).thenReturn(user);
        when(orderRepository.findAveragePriceByClientId(5))
                .thenReturn(new BigDecimal("10.456"));

        UserAnalyticsDto result = analyticsService.calculateUserAnalytics();

        assertThat(result.getUserId()).isEqualTo(5);
        assertThat(result.getAverageOrderPrice())
                .isEqualByComparingTo(new BigDecimal("10.46"));
    }

    @Test
    @DisplayName("calculatePizzaAnalytics aggregates counts, revenue and ratios")
    void shouldCalculatePizzaAnalytics() {
        // create two orders
        PizzaEntity a = new PizzaEntity(); a.setName("A"); a.setPrice(new BigDecimal("5.00"));
        PizzaEntity b = new PizzaEntity(); b.setName("B"); b.setPrice(new BigDecimal("3.00"));

        OrderEntity o1 = new OrderEntity();
        o1.setPizzas(Arrays.asList(a, b));
        o1.setDateCreated(LocalDateTime.now());

        OrderEntity o2 = new OrderEntity();
        o2.setPizzas(List.of(a));
        o2.setDateCreated(LocalDateTime.now());

        List<OrderEntity> orders = Arrays.asList(o1, o2);
        when(orderRepository.findAll()).thenReturn(orders);

        List<PizzaOrderSummaryDto.PizzaOrders> stats = analyticsService.calculatePizzaAnalytics();

        Map<String, PizzaOrderSummaryDto.PizzaOrders> map = stats.stream()
                .collect(Collectors.toMap(PizzaOrderSummaryDto.PizzaOrders::getName, p -> p));

        // totalOrders == 2

        // pizza A: count=2, revenue=10.00, ratio=2/2=1.00
        PizzaOrderSummaryDto.PizzaOrders pa = map.get("A");
        assertThat(pa.getQuantity()).isEqualTo(2);
        assertThat(pa.getTotalRevenue()).isEqualByComparingTo(new BigDecimal("10.00"));
        assertThat(pa.getOrderedRatio()).isEqualByComparingTo(new BigDecimal("1.00"));

        // pizza B: count=1, revenue=3.00, ratio=1/2=0.50
        PizzaOrderSummaryDto.PizzaOrders pb = map.get("B");
        assertThat(pb.getQuantity()).isEqualTo(1);
        assertThat(pb.getTotalRevenue()).isEqualByComparingTo(new BigDecimal("3.00"));
        assertThat(pb.getOrderedRatio()).isEqualByComparingTo(new BigDecimal("0.50"));
    }

    @Test
    @DisplayName("calculatePizzaAnalytics returns empty when no orders")
    void shouldReturnEmptyAnalyticsWhenNoOrders() {
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());

        List<PizzaOrderSummaryDto.PizzaOrders> stats = analyticsService.calculatePizzaAnalytics();
        assertThat(stats).isEmpty();
    }
}

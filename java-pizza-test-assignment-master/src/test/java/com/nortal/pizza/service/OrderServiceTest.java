package com.nortal.pizza.service;

import java.util.List;

import com.nortal.pizza.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nortal.pizza.dto.OrderDto;
import com.nortal.pizza.levels.Junior;
import com.nortal.pizza.levels.Mid;
import com.nortal.pizza.task.Assignment;
import org.mockito.InjectMocks;
import org.mockito.Mock;


/**
 * Cover OrderService with unit tests.
 * You can pick any known mocking framework
 */

@Mid(task = Assignment.TASK_01)
@Junior(task = Assignment.TASK_01)
@DisplayName(Assignment.TaskDescription.TASK_DESCRIPTION_01)
class OrderServiceTest {
    @Mock
    @InjectMocks
    /**
     * Service under test.
     */
    private OrderService orderService;
    private OrderRepository orderRepository;

    @Test
    void shouldReturnAllClientOrders() {
        Assertions.fail("Implement me");
        List<OrderDto> allClientOrders = orderService.getAllClientOrders();
    }
    @Test
    void shouldReturnAllClientOrders2() {
        Assertions.fail("Implement me");
    }

    @Test
    void shouldReturnAllClientOrders3() {
        Assertions.fail("Implement me");
    }

    @Test
    void shouldReturnAllClientOrders4() {
        Assertions.fail("Implement me");
    }

    @Test
    void shouldReturnAllClientOrders5() {
        Assertions.fail("Implement me");
    }
}
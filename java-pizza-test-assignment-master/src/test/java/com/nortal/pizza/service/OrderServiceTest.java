package com.nortal.pizza.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nortal.pizza.dto.OrderDto;
import com.nortal.pizza.levels.Junior;
import com.nortal.pizza.levels.Mid;
import com.nortal.pizza.task.Assignment;

/**
 * Cover OrderService with unit tests.
 * You can pick any known mocking framework
 */
@Mid(task = Assignment.TASK_01)
@Junior(task = Assignment.TASK_01)
@DisplayName(Assignment.TaskDescription.TASK_DESCRIPTION_01)
class OrderServiceTest {

    /**
     * Service under test.
     */
    OrderService orderService;

    @Test
    void shouldReturnAllClientOrders() {
        Assertions.fail("Implement me");
        List<OrderDto> allClientOrders = orderService.getAllClientOrders();
        //add asserts
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
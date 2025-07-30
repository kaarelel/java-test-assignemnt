package com.nortal.pizza.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.nortal.pizza.domain.*;
import com.nortal.pizza.repository.*;
import com.nortal.pizza.security.SpringSecuritySecurityContextProvider;
import com.nortal.pizza.exception.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nortal.pizza.dto.OrderDto;
import com.nortal.pizza.levels.Junior;
import com.nortal.pizza.levels.Mid;
import com.nortal.pizza.task.Assignment;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


/**
 * Cover OrderService with unit tests.
 * You can pick any known mocking framework
 */

@Mid(task = Assignment.TASK_01)
@Junior(task = Assignment.TASK_01)
@DisplayName(Assignment.TaskDescription.TASK_DESCRIPTION_01)
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SpringSecuritySecurityContextProvider securityContextProvider;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("getAllClientOrders returns empty list when no orders")
    void shouldReturnEmptyListWhenNoOrders() {
        when(securityContextProvider.getUser()).thenReturn(
                new org.springframework.security.core.userdetails.User("alice", "pass", Collections.emptyList())
        );
        when(orderRepository.findOrdersByClientId("alice")).thenReturn(Collections.emptyList());

        List<OrderDto> result = orderService.getAllClientOrders();
        assertThat(result).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("getAllClientOrders maps entities to DTOs")
    void shouldMapEntitiesToDtos() {
        when(securityContextProvider.getUser()).thenReturn(
                new org.springframework.security.core.userdetails.User("bob", "pass", Collections.emptyList())
        );
        PizzaEntity pizza1 = new PizzaEntity(); pizza1.setName("Margherita");
        PizzaEntity pizza2 = new PizzaEntity(); pizza2.setName("Pepperoni");
        OrderEntity order1 = new OrderEntity();
        order1.setId(1);
        order1.setAddress("Addr");
        order1.setDateCreated(LocalDateTime.now());
        order1.setPrice(new BigDecimal("12.00"));
        order1.setPizzas(Arrays.asList(pizza1, pizza2));

        when(orderRepository.findOrdersByClientId("bob")).thenReturn(List.of(order1));

        List<OrderDto> dtos = orderService.getAllClientOrders();

        assertThat(dtos).hasSize(1);
        OrderDto dto = dtos.get(0);
        assertThat(dto.getId()).isEqualTo(1);
        assertThat(dto.getAddress()).isEqualTo("Addr");
        assertThat(dto.getPizzas()).containsExactly("Margherita", "Pepperoni");
        assertThat(dto.getPrice()).isEqualByComparingTo(new BigDecimal("12.00"));
    }

    @Test
    @DisplayName("getById returns DTO when order exists")
    void shouldReturnDtoWhenGetByIdFound() {
        OrderEntity entity = new OrderEntity();
        entity.setId(42);
        entity.setAddress("SomeAddress");
        PizzaEntity px = new PizzaEntity(); px.setName("X");
        entity.setPizzas(List.of(px));
        entity.setPrice(new BigDecimal("5.50"));
        when(orderRepository.findById(42)).thenReturn(Optional.of(entity));

        OrderDto dto = orderService.getById(42);
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(42);
        assertThat(dto.getAddress()).isEqualTo("SomeAddress");
        assertThat(dto.getPizzas()).containsExactly("X");
        assertThat(dto.getPrice()).isEqualByComparingTo(new BigDecimal("5.50"));
    }

    @Test
    @DisplayName("getById throws when order not found")
    void shouldThrowWhenGetByIdNotFound() {
        when(orderRepository.findById(99)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> orderService.getById(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("findByOrdersAddress filters and maps correctly")
    void shouldFindByOrdersAddress() {
        PizzaEntity p = new PizzaEntity();
        p.setName("Z");
        OrderEntity e = new OrderEntity();
        e.setId(7);
        e.setAddress("Address");
        e.setPizzas(List.of(p));
        e.setPrice(new BigDecimal("3.00"));

        when(orderRepository.findByOrdersAddress("addr")).thenReturn(List.of(e));

        List<OrderDto> list = orderService.findByOrdersAddress("Addr");

        assertThat(list).hasSize(1);
        OrderDto od = list.get(0);
        assertThat(od.getAddress()).isEqualTo("Address");
        assertThat(od.getPizzas()).containsExactly("Z");
    }

    @Test
    @DisplayName("saveOrder persists and maps correctly")
    void shouldSaveOrder() {
        when(securityContextProvider.getUser()).thenReturn(
                new org.springframework.security.core.userdetails.User("dave", "pw", Collections.emptyList())
        );
        OrderDto input = OrderDto.builder().address("NewAddr").build();
        UserEntity uEnt = new UserEntity(); uEnt.setUsername("dave");
        when(userRepository.findByUsername("dave")).thenReturn(uEnt);
        OrderEntity saved = new OrderEntity();
        saved.setId(88);
        saved.setAddress("NewAddr");
        saved.setPizzas(Collections.emptyList());
        saved.setPrice(null);
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(saved);

        OrderDto out = orderService.saveOrder(input);
        assertThat(out.getId()).isEqualTo(88);
        assertThat(out.getAddress()).isEqualTo("NewAddr");
    }
}
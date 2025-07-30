package com.nortal.pizza.repository;

import com.nortal.pizza.domain.OrderEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends CrudRepository<OrderEntity, Integer> {

	@Query("SELECT o from OrderEntity o WHERE o.client.username=:username ORDER BY o.id DESC")
	List<OrderEntity> findOrdersByClientId(@Param("username") String username);

	@Query("SELECT o from OrderEntity o WHERE LOWER(o.address) LIKE %:address% ORDER BY o.id DESC")
	List<OrderEntity> findByOrdersAddress(@Param("address") String address);

	@Query("SELECT AVG(o.price) FROM OrderEntity o WHERE o.client.id = :clientId")
	BigDecimal findAveragePriceByClientId(@Param("clientId") Integer clientId);

	@Query("SELECT o FROM OrderEntity o WHERE o.dateCreated BETWEEN :startOfCurrentWeek AND :now")
	List<OrderEntity> findOrdersFromThisWeek(@Param("startOfCurrentWeek") LocalDateTime startOfCurrentWeek, @Param("now") LocalDateTime now);
}

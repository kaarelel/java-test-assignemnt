package com.nortal.pizza.repository;

import com.nortal.pizza.domain.OrderEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends CrudRepository<OrderEntity, Integer> {

	@Query("SELECT o from OrderEntity o WHERE o.client.username=:username ORDER BY o.id DESC")
	List<OrderEntity> findOrdersByClientId(@Param("username") String username);

	@Query("SELECT o from OrderEntity o WHERE LOWER(o.address) LIKE %:aadress% ORDER BY o.id DESC")
	List<OrderEntity> findByOrdersAadress(@Param("aadress") String aadress);

}

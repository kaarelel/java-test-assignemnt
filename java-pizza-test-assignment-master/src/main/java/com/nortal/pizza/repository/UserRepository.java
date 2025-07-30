package com.nortal.pizza.repository;

import com.nortal.pizza.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

	UserEntity findByUsername(String name);

}

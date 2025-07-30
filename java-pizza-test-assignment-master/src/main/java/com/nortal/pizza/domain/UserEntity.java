package com.nortal.pizza.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "enabled", nullable = false)
	private boolean enabled;

	@Column(name = "is_admin", nullable = false)
	private boolean admin;

	@Column(name = "password", nullable = false)
	private String password;

}

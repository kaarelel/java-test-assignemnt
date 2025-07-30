package com.nortal.pizza.controller;

import com.nortal.pizza.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@PreAuthorize("principal.enabled")
@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("/disable")
	public ResponseEntity<Void> disableUser(@RequestBody final Long userId) {
		return ResponseEntity.noContent().build();
	}
}

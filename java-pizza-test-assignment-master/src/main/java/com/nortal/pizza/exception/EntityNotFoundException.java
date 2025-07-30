package com.nortal.pizza.exception;

public class EntityNotFoundException extends RuntimeException {

	public EntityNotFoundException(final Object identifier) {
		super("Entity with identifier [" + identifier + "] was not found.");
	}
}

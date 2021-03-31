package com.rbiedrawa.hexagonal.app.business.common.exceptions;

public class NotFoundException extends ApplicationException {

	private static final String GENERIC_ENTITY_NOT_FOUND_MSG = "%s %s not found";

	public NotFoundException(String message) {
		super(message);
	}

	public static NotFoundException entityNotFound(String entityName, String entityIdentifier) {
		return new NotFoundException(String.format(GENERIC_ENTITY_NOT_FOUND_MSG, entityName, entityIdentifier));
	}
}

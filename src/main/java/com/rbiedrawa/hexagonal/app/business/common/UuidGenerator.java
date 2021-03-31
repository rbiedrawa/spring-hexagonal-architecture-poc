package com.rbiedrawa.hexagonal.app.business.common;

import java.util.UUID;

public interface UuidGenerator {

	static UUID generate() {
		return UUID.randomUUID();
	}

	static String generateAsString() {
		return generate().toString();
	}

	static UUID from(String uuid) {
		return UUID.fromString(uuid);
	}

}
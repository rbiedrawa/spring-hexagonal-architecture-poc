package com.rbiedrawa.hexagonal.app.business.orders.model;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Order {
	private final UUID id;
	private final String customerFullName;
	private final String orderItemName;
	private final Double totalPrice;

	public String idAsString() {
		return id.toString();
	}
}

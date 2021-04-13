package com.rbiedrawa.hexagonal.app.business.orders.models;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Order {
	private final UUID id;
	private final String customerFullName;
	private final String productName;
	private final BigDecimal totalPrice;
	private final OrderStatus status;

	public String idAsString() {
		return id.toString();
	}
}

package com.rbiedrawa.hexagonal.app.business.orders.models;

import java.math.BigDecimal;
import java.util.UUID;

import com.rbiedrawa.hexagonal.app.business.common.UuidGenerator;

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
	@Builder.Default
	private final UUID id = UuidGenerator.generate();
	private final String customerFullName;
	private final String productName;
	private final BigDecimal totalPrice;
	private final OrderStatus status;

	public String idAsString() {
		return id.toString();
	}
}

package com.rbiedrawa.hexagonal.app.business.orders.models;

import java.util.UUID;

import org.springframework.util.ObjectUtils;

import com.rbiedrawa.hexagonal.app.business.common.UuidGenerator;

import lombok.Getter;

@Getter
public class NewOrder {
	private final UUID orderId = UUID.randomUUID();
	private final UUID customerId;
	private final String orderItemName;

	private NewOrder(UUID customerId, String orderItemName) {
		this.customerId = customerId;
		this.orderItemName = orderItemName;
	}

	public static NewOrder of(String customerId, String orderItemName) throws IllegalStateException {
		if(ObjectUtils.isEmpty(customerId) || ObjectUtils.isEmpty(orderItemName)) {
			throw new IllegalStateException("Customer Id or orderItem can't be empty !!!");
		}
		return new NewOrder(UuidGenerator.from(customerId), orderItemName);
	}
}

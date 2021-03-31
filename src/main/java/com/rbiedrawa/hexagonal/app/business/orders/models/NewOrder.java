package com.rbiedrawa.hexagonal.app.business.orders.models;

import java.util.UUID;

import org.springframework.util.ObjectUtils;

import com.rbiedrawa.hexagonal.app.business.common.UuidGenerator;
import com.rbiedrawa.hexagonal.app.business.orders.OrderValidationException;

import lombok.Getter;

@Getter
public class NewOrder {
	private final UUID orderId = UUID.randomUUID();
	private final UUID customerId;
	private final String productName;

	private NewOrder(UUID customerId, String productName) {
		this.customerId = customerId;
		this.productName = productName;
	}

	public static NewOrder of(String customerId, String orderItemName) throws OrderValidationException {
		if (ObjectUtils.isEmpty(customerId) || ObjectUtils.isEmpty(orderItemName)) {
			throw OrderValidationException.missingCustomerIdOrProductName(customerId, orderItemName);
		}
		return new NewOrder(UuidGenerator.from(customerId), orderItemName);
	}

	public String customerIdAsString() {
		return customerId.toString();
	}
}

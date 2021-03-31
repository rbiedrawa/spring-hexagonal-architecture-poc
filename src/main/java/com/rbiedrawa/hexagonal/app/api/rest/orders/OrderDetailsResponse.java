package com.rbiedrawa.hexagonal.app.api.rest.orders;

import java.math.BigDecimal;

import com.rbiedrawa.hexagonal.app.business.orders.models.Order;
import com.rbiedrawa.hexagonal.app.business.orders.models.OrderStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class OrderDetailsResponse {
	String orderId;
	OrderStatus status;
	BigDecimal totalPrice;

	static OrderDetailsResponse of(final Order order) {
		return new OrderDetailsResponse(order.idAsString(), order.getStatus(), order.getTotalPrice());
	}

}

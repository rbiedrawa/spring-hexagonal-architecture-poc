package com.rbiedrawa.hexagonal.app.api.rest.orders;

import java.math.BigDecimal;

import com.rbiedrawa.hexagonal.app.business.orders.models.Order;
import com.rbiedrawa.hexagonal.app.business.orders.models.OrderStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class OrderResponse {
	String orderId;
	OrderStatus status;
	BigDecimal totalPrice;

	static OrderResponse of(final Order order) {
		return new OrderResponse(order.idAsString(), order.getStatus(), order.getTotalPrice());
	}

}

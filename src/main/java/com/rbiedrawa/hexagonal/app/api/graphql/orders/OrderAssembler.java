package com.rbiedrawa.hexagonal.app.api.graphql.orders;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

import com.rbiedrawa.hexagonal.app.api.graphql.gen.types.OrderDetails;
import com.rbiedrawa.hexagonal.app.business.orders.models.Order;

class OrderAssembler {

	static OrderDetails to(Order order) {

		double totalPrice = Optional.ofNullable(order.getTotalPrice())
									.map(BigDecimal::doubleValue)
									.orElse(0.0);

		return OrderDetails.newBuilder()
						   .id(order.idAsString())
						   .customerFullName(order.getCustomerFullName())
						   .totalPrice(totalPrice)
						   .product(order.getProductName())
						   .status(Objects.toString(order.getStatus()))
						   .build();
	}
}

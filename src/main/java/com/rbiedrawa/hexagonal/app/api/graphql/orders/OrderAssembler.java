package com.rbiedrawa.hexagonal.app.api.graphql.orders;

import java.util.Objects;

import com.rbiedrawa.hexagonal.app.api.graphql.gen.types.OrderDetails;
import com.rbiedrawa.hexagonal.app.business.orders.models.Order;

class OrderAssembler {

	public static OrderDetails to(Order order) {

		return OrderDetails.newBuilder()
						   .id(order.idAsString())
						   .customerFullName(order.getCustomerFullName())
						   .totalPrice(order.getTotalPrice().doubleValue())
						   .product(order.getProductName())
						   .status(Objects.toString(order.getStatus()))
						   .build();
	}
}

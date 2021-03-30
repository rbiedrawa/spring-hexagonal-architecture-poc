package com.rbiedrawa.hexagonal.app.business.orders;

import java.util.Optional;

import com.rbiedrawa.hexagonal.app.business.orders.model.Order;

public interface OrderRepository {

	Order save(Order order);

	Optional<Order> findById(String orderId);
}


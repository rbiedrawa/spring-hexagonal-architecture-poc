package com.rbiedrawa.hexagonal.app.postgres;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rbiedrawa.hexagonal.app.business.orders.OrderRepository;
import com.rbiedrawa.hexagonal.app.business.orders.model.Order;

@Service
class OrderPostgresRepository implements OrderRepository {

	@Override
	public Order save(Order order) {
		return order;
	}

	@Override
	public Optional<Order> findById(String orderId) {
		return Optional.empty();
	}
}

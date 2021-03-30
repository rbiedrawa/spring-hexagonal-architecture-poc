package com.rbiedrawa.hexagonal.app.business.orders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.rbiedrawa.hexagonal.app.business.orders.models.Order;

public interface OrderRepository {

	Order save(Order order);

	Optional<Order> findById(UUID orderId);

	List<Order> findAll();
}


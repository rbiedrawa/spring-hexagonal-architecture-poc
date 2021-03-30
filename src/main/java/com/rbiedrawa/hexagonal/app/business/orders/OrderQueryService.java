package com.rbiedrawa.hexagonal.app.business.orders;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rbiedrawa.hexagonal.app.business.orders.model.Order;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderQueryService {
	private final OrderRepository orderRepository;

	public Optional<Order> findById(String orderId) {
		// do some validation
		return orderRepository.findById(orderId);
	}
}

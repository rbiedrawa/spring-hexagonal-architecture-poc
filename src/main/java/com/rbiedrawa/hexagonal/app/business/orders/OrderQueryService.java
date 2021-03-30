package com.rbiedrawa.hexagonal.app.business.orders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.rbiedrawa.hexagonal.app.business.orders.models.Order;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderQueryService {
	private final OrderRepository orderRepository;

	public Optional<Order> findById(UUID orderId) {
		return orderRepository.findById(orderId);
	}

	public List<Order> findAll() {
		return orderRepository.findAll();
	}
}

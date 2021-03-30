package com.rbiedrawa.hexagonal.app.postgres.orders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rbiedrawa.hexagonal.app.business.orders.OrderRepository;
import com.rbiedrawa.hexagonal.app.business.orders.models.Order;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class OrderPostgresRepository implements OrderRepository {

	private final OrderJpaRepository jpaRepository;

	@Override
	public Order save(Order order) {
		var savedEntity = jpaRepository.save(OrderEntity.of(order));
		return savedEntity.toOrder();
	}

	@Override
	public Optional<Order> findById(UUID orderId) {
		return jpaRepository.findById(orderId)
							.map(OrderEntity::toOrder);
	}

	@Override
	public List<Order> findAll() {
		return jpaRepository.findAll()
							.stream()
							.map(OrderEntity::toOrder)
							.collect(Collectors.toList());
	}
}

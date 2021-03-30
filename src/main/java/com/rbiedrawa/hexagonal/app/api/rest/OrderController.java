package com.rbiedrawa.hexagonal.app.api.rest;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rbiedrawa.hexagonal.app.business.orders.OrderCommandService;
import com.rbiedrawa.hexagonal.app.business.orders.model.NewOrder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("orders")
class OrderController {

	private final OrderCommandService orderCommandService;

	public OrderController(OrderCommandService orderCommandService) {
		this.orderCommandService = orderCommandService;
	}

	// Customer id could be extracted from jwt token etc.
	@PostMapping
	ResponseEntity<Map<String, String>> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
		log.info("New order {} requested via REST port.", createOrderRequest);

		var newOrder = NewOrder.of(createOrderRequest.getCustomerId(), createOrderRequest.getProduct());
		var orderCreated = orderCommandService.createOrder(newOrder);

		return ResponseEntity.ok(Map.of("status", "pending"));
	}
}

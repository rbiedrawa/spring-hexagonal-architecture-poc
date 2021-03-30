package com.rbiedrawa.hexagonal.app.api.rest;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rbiedrawa.hexagonal.app.business.orders.OrderCommandService;
import com.rbiedrawa.hexagonal.app.business.orders.model.NewOrder;

@RestController
@RequestMapping("orders")
class OrderController {

	private final OrderCommandService orderCommandService;

	public OrderController(OrderCommandService orderCommandService) {
		this.orderCommandService = orderCommandService;
	}

	// Customer id could be found from jwt token
	@PostMapping
	ResponseEntity<Map<String, String>> createOrder(CreateOrderRequest createOrderRequest) {
		NewOrder newOrder = new NewOrder(createOrderRequest.getCustomerId(), createOrderRequest.getProducts());
		orderCommandService.createOrder(newOrder);

		return ResponseEntity.ok(Map.of("status", "pending"));
	}
}

package com.rbiedrawa.hexagonal.app.api.rest.orders;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rbiedrawa.hexagonal.app.business.common.UuidGenerator;
import com.rbiedrawa.hexagonal.app.business.orders.OrderCommandService;
import com.rbiedrawa.hexagonal.app.business.orders.OrderQueryService;
import com.rbiedrawa.hexagonal.app.business.orders.models.NewOrder;
import com.rbiedrawa.hexagonal.app.business.orders.models.Order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

@Slf4j
@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
class OrderController {

	private final OrderCommandService orderCommandService;

	private final OrderQueryService orderQueryService;

	@PostMapping
	OrderResponse create(@RequestBody CreateOrderRequest createOrderRequest) {
		log.info("New order {} requested via REST port.", createOrderRequest);

		var newOrder = NewOrder.of(createOrderRequest.getCustomerId(),
								   createOrderRequest.getProduct());
		var orderCreated = orderCommandService.createOrder(newOrder);

		return OrderResponse.of(orderCreated);
	}

	@PostMapping("random")
	OrderResponse createRandomOrder() {
		var customerId = UuidGenerator.generateAsString();
		return create(new CreateOrderRequest(customerId, RandomStringUtils.randomAlphabetic(20)));
	}

	@GetMapping("{orderId}")
	ResponseEntity<Order> findOne(@PathVariable String orderId) {
		log.info("Find order {} requested via REST adapter.", orderId);

		return orderQueryService.findById(UuidGenerator.from(orderId))
								.map(ResponseEntity::ok)
								.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	List<Order> findAll() {
		return orderQueryService.findAll();
	}

}

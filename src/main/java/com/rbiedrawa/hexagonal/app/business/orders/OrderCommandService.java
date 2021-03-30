package com.rbiedrawa.hexagonal.app.business.orders;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.rbiedrawa.hexagonal.app.business.customers.Customer;
import com.rbiedrawa.hexagonal.app.business.customers.CustomerService;
import com.rbiedrawa.hexagonal.app.business.orders.model.NewOrder;
import com.rbiedrawa.hexagonal.app.business.orders.model.Order;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderCommandService {

	private final OrderRepository orderRepository;
	private final CustomerService customerService;
	private final OrderNotificationService orderNotificationService;

	public Order createOrder(NewOrder newOrder) {
		if (!newOrder.isValid()) {
			throw new IllegalStateException("New order is invalid!!");
		}

		Customer customer = customerService.findById(newOrder.getCustomerId())
										   .orElseThrow(() -> new RuntimeException("Customer not found!!!"));

		if (!customer.is18yearsOld()) {
			throw new RuntimeException("Customer below 18 years old can't make order ;P");
		}

		Order order = orderRepository.save(new Order(null, customer.fullName(), newOrder.getProducts(), new Random().nextDouble()));
		orderNotificationService.sendOrderApproved(order, customer);

		return order;
	}
}

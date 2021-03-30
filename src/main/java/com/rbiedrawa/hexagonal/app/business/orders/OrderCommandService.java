package com.rbiedrawa.hexagonal.app.business.orders;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.rbiedrawa.hexagonal.app.business.customers.models.Customer;
import com.rbiedrawa.hexagonal.app.business.customers.CustomerService;
import com.rbiedrawa.hexagonal.app.business.orders.models.NewOrder;
import com.rbiedrawa.hexagonal.app.business.orders.models.Order;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderCommandService {

	private final OrderRepository orderRepository;
	private final CustomerService customerService;
	private final OrderNotificationService orderNotificationService;

	public Order createOrder(NewOrder newOrder) {
		Customer customer = customerService.findById(newOrder.getCustomerId())
										   .orElseThrow(() -> new RuntimeException("Customer not found!!!"));

		if (!customer.is18yearsOld()) {
			throw new RuntimeException("Customer below 18 years old can't make order ;P");
		}

		Order order = orderRepository.save(Order.builder()
												.id(newOrder.getOrderId())
												.customerFullName(customer.fullName())
												.orderItemName(newOrder.getOrderItemName())
												.totalPrice(BigDecimal.TEN)
												.build());

		orderNotificationService.sendOrderApproved(order, customer);

		return order;
	}
}

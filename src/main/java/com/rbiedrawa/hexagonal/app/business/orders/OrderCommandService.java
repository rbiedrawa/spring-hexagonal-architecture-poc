package com.rbiedrawa.hexagonal.app.business.orders;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.rbiedrawa.hexagonal.app.business.common.exceptions.NotFoundException;
import com.rbiedrawa.hexagonal.app.business.customers.CustomerService;
import com.rbiedrawa.hexagonal.app.business.customers.models.Customer;
import com.rbiedrawa.hexagonal.app.business.orders.models.NewOrder;
import com.rbiedrawa.hexagonal.app.business.orders.models.Order;
import com.rbiedrawa.hexagonal.app.business.orders.models.OrderStatus;

import lombok.RequiredArgsConstructor;

// @UseCase
@Service
@RequiredArgsConstructor
public class OrderCommandService {

	private final OrderRepository orderRepository;
	private final CustomerService customerService;
	private final OrderNotificationService orderNotificationService;

	public Order createOrder(NewOrder newOrder) {
		Customer customer = customerService.findById(newOrder.getCustomerId())
										   .orElseThrow(() -> NotFoundException.entityNotFound("Customer", newOrder.customerIdAsString()));

		if (!customer.is18yearsOldOrAbove()) {
			throw OrderValidationException.customerUnder18yearsOld(newOrder.customerIdAsString());
		}

		Order order = Order.builder()
						   .id(newOrder.getOrderId())
						   .customerFullName(customer.fullName())
						   .productName(newOrder.getProductName())
						   .totalPrice(BigDecimal.TEN)
						   .status(OrderStatus.APPROVED)
						   .build();

		orderNotificationService.sendOrderApproved(order, customer);

		return orderRepository.save(order);
	}
}

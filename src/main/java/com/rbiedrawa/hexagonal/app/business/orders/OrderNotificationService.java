package com.rbiedrawa.hexagonal.app.business.orders;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.rbiedrawa.hexagonal.app.business.customers.Customer;
import com.rbiedrawa.hexagonal.app.business.notifications.EmailService;
import com.rbiedrawa.hexagonal.app.business.notifications.SmsService;
import com.rbiedrawa.hexagonal.app.business.orders.model.Order;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class OrderNotificationService {

	private final EmailService emailService;
	private final SmsService smsService;

	void sendOrderApproved(Order order, Customer customer) {
		if (customer.isEmailNotificationEnabled()) {
			emailService.send(customer.getEmailAddress(), "order-approved.template", Map.of("orderId", order.getId()));
		}

		if (customer.isSmsNotificationEnabled()) {
			smsService.send(customer.getPhoneNumber(), "Your order will arrive soon !!!");
		}
	}

}

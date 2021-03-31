package com.rbiedrawa.hexagonal.app.business.orders;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.rbiedrawa.hexagonal.app.business.customers.models.Customer;
import com.rbiedrawa.hexagonal.app.business.notifications.EmailService;
import com.rbiedrawa.hexagonal.app.business.notifications.SmsService;
import com.rbiedrawa.hexagonal.app.business.orders.models.Order;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class OrderNotificationService {

	private static final String SMS_MESSAGE_FORMAT = "Your order is on its way. Order id: %s";
	private static final String ORDER_TEMPLATE_ID = "order-approved.template";

	private final EmailService emailService;
	private final SmsService smsService;

	void sendOrderApproved(Order order, Customer customer) {
		if (customer.isEmailNotificationEnabled()) {
			emailService.send(customer.getEmailAddress(), ORDER_TEMPLATE_ID, Map.of("orderId", order.idAsString()));
		}

		if (customer.isSmsNotificationEnabled()) {
			smsService.send(customer.getPhoneNumber(), String.format(SMS_MESSAGE_FORMAT, order.getId()));
		}
	}

}

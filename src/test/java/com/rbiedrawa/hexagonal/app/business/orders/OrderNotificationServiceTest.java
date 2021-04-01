package com.rbiedrawa.hexagonal.app.business.orders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Map;

import com.rbiedrawa.hexagonal.app.business.common.UuidGenerator;
import com.rbiedrawa.hexagonal.app.business.customers.models.Customer;
import com.rbiedrawa.hexagonal.app.business.notifications.EmailService;
import com.rbiedrawa.hexagonal.app.business.notifications.SmsService;
import com.rbiedrawa.hexagonal.app.business.orders.models.Order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderNotificationServiceTest {
	private final Order dummyOrder = Order.builder().id(UuidGenerator.generate()).build();

	@Mock
	private EmailService emailService;
	@Mock
	private SmsService smsService;

	private OrderNotificationService cut;

	@BeforeEach
	void setup() {
		cut = new OrderNotificationService(emailService, smsService);
	}

	@Test
	void should_send_email_when_customer_email_notification_enabled() {
		// Given
		var preferences = Map.of("emailEnabled", true);

		//When
		cut.sendOrderApproved(dummyOrder, customerWithNotificationPreferences(preferences));

		//Then
		verify(emailService).send(any(), any(), any());
		verifyNoInteractions(smsService);
	}

	@Test
	void should_send_sms_when_customer_sms_notification_enabled() {
		// Given
		var preferences = Map.of("smsEnabled", true);

		//When
		cut.sendOrderApproved(dummyOrder, customerWithNotificationPreferences(preferences));

		//Then
		verify(smsService).send(any(), any());
		verifyNoInteractions(emailService);
	}

	@Test
	void should_send_sms_and_email_when_customer_sms_and_email_notification_enabled() {
		// Given
		var preferences = Map.of("smsEnabled", true, "emailEnabled", true);

		//When
		cut.sendOrderApproved(dummyOrder, customerWithNotificationPreferences(preferences));

		//Then
		verify(emailService).send(any(), any(), any());
		verify(smsService).send(any(), any());
	}

	private Customer customerWithNotificationPreferences(Map<String, Boolean> emailEnabled) {
		return Customer.builder()
					   .notificationPreferences(emailEnabled)
					   .build();
	}
}
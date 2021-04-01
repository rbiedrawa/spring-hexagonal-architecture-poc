package com.rbiedrawa.hexagonal.app.business.orders;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import com.rbiedrawa.hexagonal.app.business.common.UuidGenerator;
import com.rbiedrawa.hexagonal.app.business.customers.CustomerService;
import com.rbiedrawa.hexagonal.app.business.customers.models.Customer;
import com.rbiedrawa.hexagonal.app.business.orders.models.NewOrder;
import com.rbiedrawa.hexagonal.app.business.orders.models.Order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderCommandServiceTest {

	private OrderCommandService cut;

	@Mock
	private OrderRepository orderRepository;
	@Mock
	private CustomerService customerService;
	@Mock
	private OrderNotificationService orderNotificationService;
	@Captor
	private ArgumentCaptor<Order> orderCaptor;

	@BeforeEach
	void setup() {
		cut = new OrderCommandService(orderRepository, customerService, orderNotificationService);
	}

	@Test
	void should_fail_when_customer_is_under_18yrs_old() {
		// Given
		var newOrder = NewOrder.of(UuidGenerator.generateAsString(), "product");

		var customerUnder18yrsOld = Customer.builder()
									.birthDate(LocalDate.now().minusYears(10))
									.build();

		when(customerService.findById(newOrder.getCustomerId())).thenReturn(Optional.of(customerUnder18yrsOld));

		// When / Then
		assertThatThrownBy(() -> cut.createOrder(newOrder))
			.isInstanceOf(OrderValidationException.class);

	}

	@Test
	void should_create_valid_product() {
		// Given
		var customerId = UuidGenerator.generateAsString();
		var newOrder = NewOrder.of(customerId, "product");

		var customer = validCustomer();

		when(customerService.findById(newOrder.getCustomerId())).thenReturn(Optional.of(customer));

		// When
		cut.createOrder(newOrder);

		// Then
		verify(orderRepository).save(orderCaptor.capture());
		verify(orderNotificationService).sendOrderApproved(any(), any());

		verifyRepositorySaveInvokedWithCorrectOrder(newOrder, customer);
	}

	private void verifyRepositorySaveInvokedWithCorrectOrder(NewOrder newOrder, Customer customer) {
		assertThat(orderCaptor.getValue().getId()).isNotNull();
		assertThat(orderCaptor.getValue().getProductName()).isEqualTo(newOrder.getProductName());
		assertThat(orderCaptor.getValue().getCustomerFullName()).isEqualTo(customer.fullName());
	}

	private Customer validCustomer() {
		return Customer.builder()
					   .firstName("Test")
					   .lastName("User")
					   .phoneNumber("202-555-0149")
					   .emailAddress("test.user@test.com")
					   .birthDate(LocalDate.now().minusYears(25))
					   .build();
	}
}
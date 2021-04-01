package com.rbiedrawa.hexagonal.app.postgres.orders;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import com.rbiedrawa.hexagonal.app.business.common.UuidGenerator;
import com.rbiedrawa.hexagonal.app.business.orders.models.Order;
import com.rbiedrawa.hexagonal.app.business.orders.models.OrderStatus;

import org.junit.jupiter.api.Test;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = OrderPostgresRepository.class))
class OrderPostgresRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private OrderPostgresRepository cut;

	@Test
	void should_save_new_order() {
		// Given
		var newOrder = Order.builder()
							.status(OrderStatus.REJECTED)
							.customerFullName("User Test")
							.productName("Test product")
							.totalPrice(BigDecimal.TEN)
							.build();

		// When
		var order = cut.save(newOrder);

		// Then
		assertThat(order.getId()).isNotNull();
		assertThat(entityManager.find(OrderEntity.class, order.getId())).isNotNull();
	}

	@Test
	@Sql("/sql/createOrder-cb1c6b78-ed68-4d68-9699-a1b6014bad55.sql")
	void should_find_order_by_id() {
		// Given
		var orderId = UuidGenerator.from("cb1c6b78-ed68-4d68-9699-a1b6014bad55");

		// When
		var order = cut.findById(orderId);

		// Then
		assertThat(order).isPresent();
	}
}
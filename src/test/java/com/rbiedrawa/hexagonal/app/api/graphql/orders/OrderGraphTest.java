package com.rbiedrawa.hexagonal.app.api.graphql.orders;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rbiedrawa.hexagonal.app.api.graphql.gen.client.CreateOrderGraphQLQuery;
import com.rbiedrawa.hexagonal.app.api.graphql.gen.client.OrderGraphQLQuery;
import com.rbiedrawa.hexagonal.app.api.graphql.gen.client.OrderProjectionRoot;
import com.rbiedrawa.hexagonal.app.api.graphql.gen.client.OrdersGraphQLQuery;
import com.rbiedrawa.hexagonal.app.api.graphql.gen.types.CreateOrder;
import com.rbiedrawa.hexagonal.app.business.common.UuidGenerator;
import com.rbiedrawa.hexagonal.app.business.orders.OrderCommandService;
import com.rbiedrawa.hexagonal.app.business.orders.OrderQueryService;
import com.rbiedrawa.hexagonal.app.business.orders.models.Order;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import org.junit.jupiter.api.Test;

@SpringBootTest(classes = {DgsAutoConfiguration.class, OrderGraph.class})
class OrderGraphTest {
	private static final OrderProjectionRoot CUSTOMER_FULL_NAME_PROJECTION = new OrderProjectionRoot().customerFullName();

	@Autowired
	private DgsQueryExecutor dgsQueryExecutor;

	@MockBean
	private OrderCommandService orderCommandService;

	@MockBean
	private OrderQueryService orderQueryService;

	@Test
	void should_find_order_by_id() {
		// Given
		var orderId = UuidGenerator.generate();
		var customerName = "Test User";

		when(orderQueryService.findById(orderId))
			   .thenReturn(Optional.of(Order.builder().id(orderId).customerFullName(customerName).build()));

		// When
		var orderByIdQuery = OrderGraphQLQuery.newRequest()
											  .orderId(orderId.toString())
											  .build();
		var graphQLQueryRequest = new GraphQLQueryRequest(orderByIdQuery, CUSTOMER_FULL_NAME_PROJECTION);

		var result = dgsQueryExecutor.executeAndGetDocumentContext(graphQLQueryRequest.serialize());

		// Then
		assertThat(result).isNotNull();
		assertThat(result.read("data.order.customerFullName", String.class)).isEqualTo(customerName);
	}

	@Test
	void should_find_all_orders() {
		// Given
		var orderId = UuidGenerator.generate();
		var customerName = "Test User";

		when(orderQueryService.findAll())
			.thenReturn(List.of(Order.builder().id(orderId).customerFullName(customerName).build()));

		// When
		var findAllOrders = OrdersGraphQLQuery.newRequest().build();

		var graphQLQueryRequest = new GraphQLQueryRequest(findAllOrders, CUSTOMER_FULL_NAME_PROJECTION);

		var jsonPath = "data.orders[*].customerFullName";

		List<String> result = dgsQueryExecutor.executeAndExtractJsonPath(graphQLQueryRequest.serialize(), jsonPath);

		// Then
		assertThat(result).isNotEmpty()
						  .containsAll(List.of(customerName));
	}

	@Test
	void should_create_new_order() {
		// Given
		var productName = "Test Product";

		when(orderCommandService.createOrder(any())).thenReturn(Order.builder().productName(productName).build());

		var createOrder = CreateOrder.newBuilder()
											 .customerId(UuidGenerator.generateAsString())
											 .product(productName)
											 .build();

		// When
		var createOrderQuery = CreateOrderGraphQLQuery.newRequest()
										   .createOrder(createOrder)
										   .build();

		var result = dgsQueryExecutor.execute(new GraphQLQueryRequest(createOrderQuery, new OrderProjectionRoot().product()).serialize());

		// Then
		assertThat(result.isDataPresent()).isTrue();
	}
}
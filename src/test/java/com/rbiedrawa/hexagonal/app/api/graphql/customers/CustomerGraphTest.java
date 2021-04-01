package com.rbiedrawa.hexagonal.app.api.graphql.customers;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rbiedrawa.hexagonal.app.api.graphql.gen.client.CustomerGraphQLQuery;
import com.rbiedrawa.hexagonal.app.api.graphql.gen.client.CustomerProjectionRoot;
import com.rbiedrawa.hexagonal.app.business.common.UuidGenerator;
import com.rbiedrawa.hexagonal.app.business.common.exceptions.NotFoundException;
import com.rbiedrawa.hexagonal.app.business.customers.CustomerService;
import com.rbiedrawa.hexagonal.app.business.customers.models.Customer;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import org.junit.jupiter.api.Test;

@SpringBootTest(classes = {DgsAutoConfiguration.class, CustomerGraph.class})
class CustomerGraphTest {

	@Autowired
	private DgsQueryExecutor dgsQueryExecutor;

	@MockBean
	private CustomerService customerService;

	@Test
	void should_return_existing_customer() {
		// Given
		var customer = Customer.builder().id(UuidGenerator.generate()).emailAddress("test@test.com").build();

		var graphQLQueryRequest = customerQueryRequestWithProjection(customer.idAsString());

		given(customerService.findById(customer.getId())).willReturn(Optional.of(customer));

		// When
		var result = dgsQueryExecutor.executeAndGetDocumentContext(graphQLQueryRequest.serialize());

		// Then
		then(customerService).should().findById(customer.getId());

		assertThat(result).isNotNull();
		assertThat(result.read("data.customer.id", String.class)).isEqualTo(customer.idAsString());
		assertThat(result.read("data.customer.emailAddress", String.class)).isEqualTo(customer.getEmailAddress());
	}

	@Test
	void should_handle_not_found_exception() {
		// Given
		var customerId = UuidGenerator.from("ef462c27-6f83-44b6-971c-7c77d6912ad9");
		var customerQueryRequest = customerQueryRequestWithProjection(customerId.toString());

		given(customerService.findById(customerId)).willThrow(NotFoundException.entityNotFound("Customer", customerId.toString()));

		// When
		var result = dgsQueryExecutor.execute(customerQueryRequest.serialize());

		// Then
		assertThat(result.getErrors()).hasSize(1);
	}

	private GraphQLQueryRequest customerQueryRequestWithProjection(String customerId) {
		var customerByIdQuery = CustomerGraphQLQuery.newRequest()
													.customerId(customerId)
													.build();

		var projection = new CustomerProjectionRoot().id().emailAddress();

		return new GraphQLQueryRequest(customerByIdQuery, projection);
	}
}
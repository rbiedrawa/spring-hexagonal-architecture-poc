package com.rbiedrawa.hexagonal.app.api.graphql.customers;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;
import java.util.UUID;

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
	DgsQueryExecutor dgsQueryExecutor;

	@MockBean CustomerService customerService;

	@Test
	void should_return_existing_customer() {
		// Given
		var customer = Customer.builder().id(UuidGenerator.generate()).emailAddress("test@test.com").build();

		given(customerService.findById(customer.getId())).willReturn(Optional.of(customer));

		var customerByIdQuery = CustomerGraphQLQuery.newRequest()
													.customerId(customer.idAsString())
													.build();

		var projection = new CustomerProjectionRoot().id().emailAddress();
		// When
		var result = dgsQueryExecutor.executeAndGetDocumentContext(new GraphQLQueryRequest(customerByIdQuery, projection).serialize());

		// Then
		then(customerService).should().findById(customer.getId());


		assertThat(result).isNotNull();
		assertThat(result.read("data.customer.id", String.class)).isEqualTo(customer.idAsString());
		assertThat(result.read("data.customer.emailAddress", String.class)).isEqualTo(customer.getEmailAddress());
	}

	@Test
	void should_handle_not_found_exception() {
		// Given
		UUID customerId = UuidGenerator.from("ef462c27-6f83-44b6-971c-7c77d6912ad9");

		given(customerService.findById(customerId)).willThrow(NotFoundException.entityNotFound("Customer", customerId.toString()));

		var customerByIdQuery = CustomerGraphQLQuery.newRequest()
													.customerId(customerId.toString())
													.build();

		var projection = new CustomerProjectionRoot().id().emailAddress();


		// When
		var result = dgsQueryExecutor.execute(new GraphQLQueryRequest(customerByIdQuery, projection).serialize());

		// Then
		assertThat(result.getErrors()).hasSize(1);
	}
}
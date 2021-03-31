package com.rbiedrawa.hexagonal.app.api.graphql.customers;


import com.rbiedrawa.hexagonal.app.api.graphql.gen.DgsConstants;
import com.rbiedrawa.hexagonal.app.api.graphql.gen.types.CustomerDetails;
import com.rbiedrawa.hexagonal.app.business.common.UuidGenerator;
import com.rbiedrawa.hexagonal.app.business.common.exceptions.NotFoundException;
import com.rbiedrawa.hexagonal.app.business.customers.CustomerService;
import com.rbiedrawa.hexagonal.app.business.customers.models.Customer;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class CustomerGraph {

	private final CustomerService customerService;

	@DgsData(parentType = DgsConstants.QUERY.TYPE_NAME, field = DgsConstants.QUERY.CustomerById)
	CustomerDetails findById(@InputArgument("customerId") String customerId) {

		return customerService.findById(UuidGenerator.from(customerId))
							  .map(this::to)
							  .orElseThrow(() -> NotFoundException.entityNotFound("Customer", customerId));
	}

	private CustomerDetails to(Customer customer) {
		return CustomerDetails.newBuilder()
							  .id(customer.idAsString())
							  .birthDate(customer.getBirthDate().toString())
							  .emailAddress(customer.getEmailAddress())
							  .firstName(customer.getFirstName())
							  .lastName(customer.getLastName())
							  .phoneNumber(customer.getPhoneNumber())
							  .build();
	}

}

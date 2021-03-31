package com.rbiedrawa.hexagonal.app.api.graphql.customers;


import java.time.LocalDate;
import java.util.Optional;

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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DgsComponent
@RequiredArgsConstructor
public class CustomerGraph {

	private final CustomerService customerService;

	@DgsData(parentType = DgsConstants.QUERY.TYPE_NAME, field = DgsConstants.QUERY.Customer)
	public CustomerDetails findById(@InputArgument("customerId") String customerId) {
		log.info("Find customer {} requested via Graphql adapter.", customerId);

		return customerService.findById(UuidGenerator.from(customerId))
							  .map(this::to)
							  .orElseThrow(() -> NotFoundException.entityNotFound("Customer", customerId));
	}

	private CustomerDetails to(Customer customer) {
		var birthDate = Optional.ofNullable(customer.getBirthDate())
								   .map(LocalDate::toString)
								   .orElse(null);

		return CustomerDetails.newBuilder()
							  .id(customer.idAsString())
							  .birthDate(birthDate)
							  .emailAddress(customer.getEmailAddress())
							  .firstName(customer.getFirstName())
							  .lastName(customer.getLastName())
							  .phoneNumber(customer.getPhoneNumber())
							  .build();
	}

}

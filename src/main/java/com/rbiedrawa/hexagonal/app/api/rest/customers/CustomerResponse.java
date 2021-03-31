package com.rbiedrawa.hexagonal.app.api.rest.customers;

import java.time.LocalDate;

import com.rbiedrawa.hexagonal.app.business.customers.models.Customer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class CustomerResponse {
	String id;
	String emailAddress;
	LocalDate birthDate;
	String firstName;
	String lastName;
	String phoneNumber;

	private static CustomerResponse of(Customer customer) {
		return CustomerResponse.builder()
							   .id(customer.getId().toString())
							   .emailAddress(customer.getEmailAddress())
							   .birthDate(customer.getBirthDate())
							   .firstName(customer.getFirstName())
							   .lastName(customer.getLastName())
							   .phoneNumber(customer.getPhoneNumber())
							   .build();
	}

}

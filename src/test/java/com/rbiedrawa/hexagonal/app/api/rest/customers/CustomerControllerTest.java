package com.rbiedrawa.hexagonal.app.api.rest.customers;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import com.rbiedrawa.hexagonal.app.api.rest.config.GlobalExceptionHandler;
import com.rbiedrawa.hexagonal.app.business.common.UuidGenerator;
import com.rbiedrawa.hexagonal.app.business.common.exceptions.NotFoundException;
import com.rbiedrawa.hexagonal.app.business.customers.CustomerService;
import com.rbiedrawa.hexagonal.app.business.customers.models.Customer;

import org.junit.jupiter.api.Test;

@WebMvcTest(controllers = {CustomerController.class, GlobalExceptionHandler.class})
class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerService customerService;

	@Test
	void should_return_existing_customer() throws Exception {
		// Given
		var customer = Customer.builder().id(UuidGenerator.generate())
							   .emailAddress("test@test.com").build();

		when(customerService.findById(customer.getId())).thenReturn(Optional.of(customer));

		// When
		var result = mockMvc.perform(get("/customers/{customerId}", customer.idAsString()));

		// Then
		result.andExpect(status().is2xxSuccessful())
			  .andExpect(jsonPath("id").value(customer.getId().toString()))
			  .andExpect(jsonPath("emailAddress").value(customer.getEmailAddress()));
	}

	@Test
	void should_handle_not_found_exception() throws Exception {
		// Given
		var customerId = UuidGenerator.from("ef462c27-6f83-44b6-971c-7c77d6912ad9");

		when(customerService.findById(customerId)).thenThrow(NotFoundException.entityNotFound("Customer", customerId.toString()));

		// When
		var result = mockMvc.perform(get("/customers/{customerId}", customerId.toString()));

		// Then
		result.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}
}
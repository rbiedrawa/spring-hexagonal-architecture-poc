package com.rbiedrawa.hexagonal.app.api.rest.customers;

import javax.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rbiedrawa.hexagonal.app.business.common.UuidGenerator;
import com.rbiedrawa.hexagonal.app.business.customers.CustomerService;
import com.rbiedrawa.hexagonal.app.business.customers.models.Customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
class CustomerController {

	private final CustomerService customerService;

	@GetMapping("{customerId}")
	Customer findById(@PathVariable String customerId) {
		log.info("Find customer {} requested via REST adapter.", customerId);

		return customerService.findById(UuidGenerator.from(customerId))
							   .orElseThrow(() -> new EntityNotFoundException("test"));
	}
}

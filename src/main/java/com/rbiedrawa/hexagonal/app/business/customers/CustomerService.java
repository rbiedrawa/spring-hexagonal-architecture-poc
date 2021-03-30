package com.rbiedrawa.hexagonal.app.business.customers;

import java.util.Optional;
import java.util.UUID;

import com.rbiedrawa.hexagonal.app.business.customers.models.Customer;

public interface CustomerService {
	Optional<Customer> findById(UUID userId);
}

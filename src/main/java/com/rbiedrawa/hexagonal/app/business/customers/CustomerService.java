package com.rbiedrawa.hexagonal.app.business.customers;

import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
	Optional<Customer> findById(UUID userId);
}

package com.rbiedrawa.hexagonal.app.business.customers;

import java.util.Optional;

public interface CustomerService {
	Optional<Customer> findById(String userId);
}

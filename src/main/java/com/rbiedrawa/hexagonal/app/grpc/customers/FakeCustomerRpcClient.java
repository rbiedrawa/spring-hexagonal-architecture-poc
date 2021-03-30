package com.rbiedrawa.hexagonal.app.grpc.customers;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.rbiedrawa.hexagonal.app.business.customers.models.Customer;
import com.rbiedrawa.hexagonal.app.business.customers.CustomerService;

import com.github.javafaker.Faker;

/**
 * Fake gRPC client, just for demo
 */
@Service
class FakeCustomerRpcClient implements CustomerService {

	private static final Map<UUID, Customer> CUSTOMERS_DATA = new ConcurrentHashMap<>();

	@Override
	public Optional<Customer> findById(UUID userId) {
		return Optional.ofNullable(CUSTOMERS_DATA.get(userId))
					   .or(() -> createFakeCustomer(userId));
	}

	private Optional<Customer> createFakeCustomer(UUID userId) {
		Faker faker = new Faker();

		Customer fakeCustomer = Customer.builder()
										.id(userId)
										.firstName(faker.name().firstName())
										.lastName(faker.name().lastName())
										.birthDate(LocalDate.ofInstant(faker.date().birthday(20, 55).toInstant(), ZoneOffset.UTC))
										.emailAddress(faker.internet().emailAddress())
										.phoneNumber(faker.phoneNumber().phoneNumber())
										.notificationPreferences(Map.of("smsEnabled", true, "emailEnabled", true))
										.build();

		CUSTOMERS_DATA.put(fakeCustomer.getId(), fakeCustomer);

		return Optional.of(fakeCustomer);
	}

}

package com.rbiedrawa.hexagonal.app.spi.grpc.customers;

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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
class FakeCustomerRpcClient implements CustomerService {

	private static final Map<UUID, Customer> CUSTOMERS_GRPC_SERVER = new ConcurrentHashMap<>();

	@Override
	public Optional<Customer> findById(UUID customerId) {
		// Real implementation goes here

		log.info("Searching customer {} via gRPC adapter", customerId);
		return Optional.ofNullable(CUSTOMERS_GRPC_SERVER.get(customerId))
					   .or(() -> createFakeCustomer(customerId));
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

		CUSTOMERS_GRPC_SERVER.put(fakeCustomer.getId(), fakeCustomer);

		log.info("Stored fake user. UserId: {}", fakeCustomer.getId());
		return Optional.of(fakeCustomer);
	}

}

package com.rbiedrawa.hexagonal.app.api.rest.orders;

import lombok.Value;

@Value
class CreateOrderRequest {
	String customerId;
	String product;
}

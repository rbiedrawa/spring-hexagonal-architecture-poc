package com.rbiedrawa.hexagonal.app.api.rest;

import lombok.Value;

@Value
public class CreateOrderRequest {
	String customerId;
	String product;
}

package com.rbiedrawa.hexagonal.app.api.rest;

import java.util.List;

import lombok.Value;

@Value
public class CreateOrderRequest {
	String customerId;
	List<String> products;
}

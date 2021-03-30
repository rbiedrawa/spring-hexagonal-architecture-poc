package com.rbiedrawa.hexagonal.app.business.orders.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	private String id;
	private String customerFullName;
	private List<String> products;
	private Double totalPrice;
}

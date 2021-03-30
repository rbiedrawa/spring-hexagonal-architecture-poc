package com.rbiedrawa.hexagonal.app.business.orders.model;

import java.util.List;

import org.springframework.util.ObjectUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewOrder {
	private String customerId;
	private List<String> products;

	public boolean isValid() {
		return !ObjectUtils.isEmpty(customerId) && products != null && !products.isEmpty();
	}
}

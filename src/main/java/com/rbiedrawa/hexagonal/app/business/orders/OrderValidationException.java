package com.rbiedrawa.hexagonal.app.business.orders;

import com.rbiedrawa.hexagonal.app.business.common.exceptions.ApplicationException;

public class OrderValidationException extends ApplicationException {

	private static final String CUSTOMER_UNDER_18_YEARS_OLD_ERROR = "Customer %s under 18 years old!!!";

	private OrderValidationException(String message) {
		super(message);
	}

	public static OrderValidationException customerUnder18yearsOld(String customerId) {
		return new OrderValidationException(String.format(CUSTOMER_UNDER_18_YEARS_OLD_ERROR, customerId));
	}

	public static OrderValidationException missingCustomerIdOrProductName(String customerId, String productName) {
		return new OrderValidationException("Missing customer id or product name!!!");
	}

}

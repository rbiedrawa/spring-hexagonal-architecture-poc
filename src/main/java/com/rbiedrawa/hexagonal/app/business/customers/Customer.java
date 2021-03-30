package com.rbiedrawa.hexagonal.app.business.customers;

import java.time.LocalDate;
import java.time.Period;
import java.util.Map;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Customer{
	private UUID id;
	private String emailAddress;
	private LocalDate birthDate;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private Map<String, Boolean> notificationPreferences;

	public boolean isSmsNotificationEnabled() {
		return notificationPreferences.containsKey("smsEnabled") &&
		notificationPreferences.get("smsEnabled");
	}

	public boolean isEmailNotificationEnabled() {
		return notificationPreferences.containsKey("emailEnabled") &&
			   notificationPreferences.get("emailEnabled");
	}

	public boolean is18yearsOld() {
		return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
	}

	public String fullName() {
		return String.format("%s %s", firstName, lastName);
	}

}

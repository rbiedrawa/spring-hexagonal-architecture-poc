package com.rbiedrawa.hexagonal.app.business.notifications;

public interface SmsService {
	void send(String phoneNumber, String message);
}

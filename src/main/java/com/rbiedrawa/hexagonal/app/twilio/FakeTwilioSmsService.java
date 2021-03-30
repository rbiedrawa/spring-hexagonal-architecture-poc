package com.rbiedrawa.hexagonal.app.twilio;

import org.springframework.stereotype.Service;

import com.rbiedrawa.hexagonal.app.business.notifications.SmsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
class FakeTwilioSmsService implements SmsService {

	@Override
	public void send(String phoneNumber, String message) {
		log.info("Sending 'order approved' notification using Twilio!!!");
	}
}

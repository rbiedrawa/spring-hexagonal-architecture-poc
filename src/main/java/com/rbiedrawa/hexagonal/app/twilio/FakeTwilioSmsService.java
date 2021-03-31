package com.rbiedrawa.hexagonal.app.twilio;

import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.rbiedrawa.hexagonal.app.business.notifications.SmsService;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
class FakeTwilioSmsService implements SmsService {

	@Async
	@Override
	public void send(String phoneNumber, String message) {
		simulateRemoteCall();
		log.info("Successfully sent sms to: {} from Twilio SMS service.", phoneNumber);
	}

	@SneakyThrows
	private void simulateRemoteCall() {
		TimeUnit.SECONDS.sleep(2);
	}
}

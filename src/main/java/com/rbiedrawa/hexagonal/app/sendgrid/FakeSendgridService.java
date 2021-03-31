package com.rbiedrawa.hexagonal.app.sendgrid;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.rbiedrawa.hexagonal.app.business.notifications.EmailService;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
class FakeSendgridService implements EmailService {

	@Async
	@Override
	public void send(String emailAddress, String templateId, Map<String, String> templateVariables) {
		simulateRemoteCall();
		log.info("Successfully sent '{}' email to: {}.",templateId, emailAddress);
	}

	@SneakyThrows
	private void simulateRemoteCall() {
		TimeUnit.SECONDS.sleep(2);
	}
}

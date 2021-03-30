package com.rbiedrawa.hexagonal.app.sendgrid;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.rbiedrawa.hexagonal.app.business.notifications.EmailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
class FakeSendgridService implements EmailService {

	@Override
	public void send(String emailAddress, String templateId, Map<String, String> templateVariables) {
		log.info("Sending 'order approved' using Sendgrid!!!");
	}
}
